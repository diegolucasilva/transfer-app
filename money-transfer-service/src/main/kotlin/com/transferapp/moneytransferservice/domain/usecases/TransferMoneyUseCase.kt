package com.transferapp.moneytransferservice.domain.usecases

import com.transferapp.moneytransferservice.adapter.utils.toJson
import com.transferapp.moneytransferservice.adapter.out.dto.CustomerDTOResponse
import com.transferapp.moneytransferservice.adapter.out.exception.RequestDeniedException
import com.transferapp.moneytransferservice.adapter.utils.toTransferMoneyAccountServiceDTORequest
import com.transferapp.moneytransferservice.domain.entity.Transaction
import com.transferapp.moneytransferservice.domain.port.out.dto.*
import org.springframework.stereotype.Service
import org.springframework.validation.FieldError
import java.util.concurrent.CompletableFuture

@Service
class TransferMoneyUseCase(
    private val saveTransactionPort: SaveTransactionPort,
    private val getCustomerByDocumentIdPort: GetCustomerByDocumentIdServicePort,
    private val getAuthorizationFromAuthorizerXPTOServicePort: GetAuthorizationFromAuthorizerXPTOServicePort,
    private val transactionTransferMoneyServicePort: TransactionTransferMoneyServicePort,
    private val sendNotificationPort: SendNotificationPort,

    ) {
    private val CUSTOMER_INACTIVE= 0
    private val AUTORIZADO = "Autorizado"

    fun execute(transaction: Transaction): Transaction{
        val transactionSaved = saveTransactionPort.save(transaction)
        val customerFromAccount = CompletableFuture.supplyAsync {
            getCustomerByDocumentIdPort.getByDocumentId(transaction.fromCustomer.documentId)
        }
        val customerToAccount =  CompletableFuture.supplyAsync {
            getCustomerByDocumentIdPort.getByDocumentId(transaction.toCustomer.documentId)
        }

        val fieldErrorsList  = validadeIfTransactionIsValid(customerFromAccount.get(), customerToAccount.get())
        if(fieldErrorsList.isNotEmpty()){
            transactionSaved.denied()
            saveTransactionPort.save(transactionSaved)
            throw RequestDeniedException(fieldErrorsList)
        }

        val response= getAuthorizationFromAuthorizerXPTOServicePort.getAuthorization(transaction)
        if(response.message != AUTORIZADO){
            transactionSaved.denied()
            saveTransactionPort.save(transactionSaved)
            throw RequestDeniedException(description = "transaction not authorized")
        }
        transactionTransferMoneyServicePort.transactionTransferMoney(transaction.toTransferMoneyAccountServiceDTORequest())
        transactionSaved.confirm()
        saveTransactionPort.save(transactionSaved)
        sendNotificationPort.send(transactionSaved.toJson())
        return transactionSaved
    }

    private fun validadeIfTransactionIsValid(customerFromAccount: CustomerDTOResponse, customerToAccount: CustomerDTOResponse):MutableList<FieldError> {
        val fieldErrors: MutableList<FieldError> = mutableListOf()
        if(customerFromAccount.role == CustomerDTOResponse.RoleType.SHOP_KEEPER){
            fieldErrors.add(FieldError("", "", "Transaction from shop keepers not allow"))
        }
        else if(customerFromAccount.status == CUSTOMER_INACTIVE) {
            fieldErrors.add(FieldError("", "customerFromAccount", "Customer inactive. Transaction not allow"))
        }
        if (customerToAccount.status == CUSTOMER_INACTIVE) {
            fieldErrors.add(FieldError("", "customerToAccount", "Customer inactive. Transaction not allow"))
        }
        return fieldErrors
    }
}