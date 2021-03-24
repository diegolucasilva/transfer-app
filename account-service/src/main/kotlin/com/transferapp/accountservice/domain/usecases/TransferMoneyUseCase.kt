package com.transferapp.accountservice.domain.usecases

import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.adapter.out.persistence.exception.ErrorResponse
import com.transferapp.accountservice.adapter.out.persistence.exception.InvalidAccountException
import com.transferapp.accountservice.adapter.utls.toDomain
import com.transferapp.accountservice.domain.entity.TransferMoney
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByNumberPort
import com.transferapp.accountservice.domain.port.out.persistence.TransactionTransferMoneyPort
import org.springframework.stereotype.Service
import org.springframework.validation.FieldError
import java.util.*

@Service
class TransferMoneyUseCase(
    private val getAccountByNumberPort: GetAccountByNumberPort,
    private val transactionTransferMoneyPort: TransactionTransferMoneyPort
) {

    fun execute(transferMoney: TransferMoney){
        val fromAccountEntity = getAccountByNumberPort.getByNumber(transferMoney.fromAccountId)
        val toAccountEntity = getAccountByNumberPort.getByNumber(transferMoney.toAccountId)

        checkIfAccountsExists(fromAccountEntity, toAccountEntity)
        checkIfAccountsIsNotEquals(fromAccountEntity.get().number, toAccountEntity.get().number)

        val fromAccount = fromAccountEntity.get().toDomain()
        val toAccount = toAccountEntity.get().toDomain()

        fromAccount.debit(transferMoney.amount)
        toAccount.credit(transferMoney.amount)

        transactionTransferMoneyPort.transactionTransferMoney(fromAccount, toAccount)
    }

    private fun checkIfAccountsExists(fromAccountEntity: Optional<AccountEntity>, toAccountEntity: Optional<AccountEntity>){
        val fieldErrors: MutableList<FieldError> = mutableListOf()
        if(fromAccountEntity.isEmpty){
            fieldErrors.add(FieldError("", "fromAccountId", "this account does not exist"))
        }
        if(toAccountEntity.isEmpty){
            fieldErrors.add(FieldError("", "toAccountEntity", "this account does not exist"))
        }
        if(fieldErrors.isNotEmpty())
            throw  InvalidAccountException(fieldErrors)
    }

    private fun checkIfAccountsIsNotEquals(fromAccount: String, toAccount: String){
        if(fromAccount == toAccount){
            throw  InvalidAccountException(description =  "the both account can't not be equals")
        }
    }


}