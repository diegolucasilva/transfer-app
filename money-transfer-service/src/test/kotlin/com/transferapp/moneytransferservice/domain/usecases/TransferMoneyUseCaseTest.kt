package com.transferapp.moneytransferservice.domain.usecases

import com.nhaarman.mockitokotlin2.*
import com.transferapp.moneytransferservice.adapter.out.dto.AuthorizerXPTOResponse
import com.transferapp.moneytransferservice.adapter.out.dto.CustomerDTOResponse
import com.transferapp.moneytransferservice.adapter.out.exception.RequestDeniedException
import com.transferapp.moneytransferservice.domain.entity.Transaction
import com.transferapp.moneytransferservice.domain.port.out.dto.GetAuthorizationFromAuthorizerXPTOServicePort
import com.transferapp.moneytransferservice.domain.port.out.dto.GetCustomerByDocumentIdServicePort
import com.transferapp.moneytransferservice.domain.port.out.dto.SaveTransactionPort
import com.transferapp.moneytransferservice.domain.port.out.dto.TransactionTransferMoneyServicePort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class TransferMoneyUseCaseTest{

    private val saveTransactionPort: SaveTransactionPort = mock()
    private val getCustomerByDocumentIdPort: GetCustomerByDocumentIdServicePort = mock()
    private val getAuthorizationFromAuthorizerXPTOServicePort: GetAuthorizationFromAuthorizerXPTOServicePort = mock()
    private val transactionTransferMoneyServicePort: TransactionTransferMoneyServicePort = mock()

    private val transferMoneyUseCase = TransferMoneyUseCase(saveTransactionPort,getCustomerByDocumentIdPort, getAuthorizationFromAuthorizerXPTOServicePort, transactionTransferMoneyServicePort)

    @Test
    fun `When use case is executed should transfer money`(){
        //GIVEN
        val transaction = givenTransaction()

        //MOCK
        val fromAccount = givenAccountResponse(transaction.fromAccountId,1, CustomerDTOResponse.RoleType.USER)
        val toAccount = givenAccountResponse(transaction.toAccountId,1, CustomerDTOResponse.RoleType.USER)
        val authorizerResponse = givenAuthorizerXPTO("Autorizado")
        whenever(saveTransactionPort.save(transaction)).thenReturn(transaction)
        whenever(getCustomerByDocumentIdPort.getByDocumentId(transaction.fromAccountId)).thenReturn(fromAccount)
        whenever(getCustomerByDocumentIdPort.getByDocumentId(transaction.toAccountId)).thenReturn(toAccount)
        whenever(getAuthorizationFromAuthorizerXPTOServicePort.getAuthorization(transaction)).thenReturn(authorizerResponse)

        //WHEN
        val transactionResponse = transferMoneyUseCase.execute(transaction)

        //THEN
        verify(saveTransactionPort, times(2)).save(any<Transaction>())
        verify(getCustomerByDocumentIdPort, times(1)).getByDocumentId(transaction.fromAccountId)
        verify(getCustomerByDocumentIdPort, times(1)).getByDocumentId(transaction.toAccountId)
        verify(getAuthorizationFromAuthorizerXPTOServicePort, times(1)).getAuthorization(transaction)
        Assertions.assertEquals(transactionResponse.status, Transaction.Status.OK)
    }

    @Test
    fun `When an origin account is a SHOP_KEEP should deny transaction`(){
        //GIVEN
        val transaction = givenTransaction()

        //MOCK
        val fromAccount = givenAccountResponse(transaction.fromAccountId,1, CustomerDTOResponse.RoleType.SHOP_KEEPER)
        val toAccount = givenAccountResponse(transaction.toAccountId,1, CustomerDTOResponse.RoleType.USER)
        whenever(saveTransactionPort.save(transaction)).thenReturn(transaction)
        whenever(getCustomerByDocumentIdPort.getByDocumentId(transaction.fromAccountId)).thenReturn(fromAccount)
        whenever(getCustomerByDocumentIdPort.getByDocumentId(transaction.toAccountId)).thenReturn(toAccount)

        //WHEN
        val thrown = Assertions.assertThrows(RequestDeniedException::class.java) {
            val transactionResponse = transferMoneyUseCase.execute(transaction)
            Assertions.assertEquals(transactionResponse.status, Transaction.Status.DENIED)
        }

        //THEN
        Assertions.assertEquals("Transaction from shop keepers not allow", thrown.fieldErrors[0].defaultMessage)
        verify(saveTransactionPort, times(2)).save(any<Transaction>())
        verify(getCustomerByDocumentIdPort, times(1)).getByDocumentId(transaction.fromAccountId)
        verify(getCustomerByDocumentIdPort, times(1)).getByDocumentId(transaction.toAccountId)
    }

    @Test
    fun `When  account is INACTIVE should deny transaction`(){
        //GIVEN
        val transaction = givenTransaction()

        //MOCK
        val fromAccount = givenAccountResponse(transaction.fromAccountId,0, CustomerDTOResponse.RoleType.USER)
        val toAccount = givenAccountResponse(transaction.toAccountId,0, CustomerDTOResponse.RoleType.SHOP_KEEPER)
        whenever(saveTransactionPort.save(transaction)).thenReturn(transaction)
        whenever(getCustomerByDocumentIdPort.getByDocumentId(transaction.fromAccountId)).thenReturn(fromAccount)
        whenever(getCustomerByDocumentIdPort.getByDocumentId(transaction.toAccountId)).thenReturn(toAccount)

        //WHEN
        val thrown = Assertions.assertThrows(RequestDeniedException::class.java) {
            val transactionResponse = transferMoneyUseCase.execute(transaction)
            Assertions.assertEquals(transactionResponse.status, Transaction.Status.DENIED)
        }

        //THEN
        Assertions.assertEquals("Customer inactive. Transaction not allow", thrown.fieldErrors[0].defaultMessage)
        Assertions.assertEquals("customerFromAccount", thrown.fieldErrors[0].field)
        Assertions.assertEquals("Customer inactive. Transaction not allow", thrown.fieldErrors[1].defaultMessage)
        Assertions.assertEquals("customerToAccount", thrown.fieldErrors[1].field)
        verify(saveTransactionPort, times(2)).save(any<Transaction>())
        verify(getCustomerByDocumentIdPort, times(1)).getByDocumentId(transaction.fromAccountId)
        verify(getCustomerByDocumentIdPort, times(1)).getByDocumentId(transaction.toAccountId)
    }

    private fun givenAccountResponse(documentId: String, status: Int, role: CustomerDTOResponse.RoleType): CustomerDTOResponse {
        return CustomerDTOResponse(
            "1",
             "John",
            "john@gmail.com",
            documentId,
            status,
            role)
    }

    private fun givenAuthorizerXPTO(response:String) = AuthorizerXPTOResponse(response)


    private fun givenTransaction(): Transaction {
        return Transaction(
            id  ="123",
            fromAccountId = "123",
            toAccountId = "124",
            amount = 3232.30,
        )
    }
}