package com.transferapp.accountservice.domain.usecases

import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.adapter.out.persistence.exception.InvalidAccountException
import com.transferapp.accountservice.adapter.utls.toDomain
import com.transferapp.accountservice.domain.entity.TransferMoney
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByNumberPort
import com.transferapp.accountservice.domain.port.out.persistence.TransactionTransferMoneyPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransferMoneyUseCase(
    private val getAccountByNumberPort: GetAccountByNumberPort,
    private val transactionTransferMoneyPort: TransactionTransferMoneyPort
) {

    fun execute(transferMoney: TransferMoney){
        val fromAccountEntity = getAccountByNumberPort.getByNumber(transferMoney.fromAccountId)
        val toAccountEntity = getAccountByNumberPort.getByNumber(transferMoney.toAccountId)

        if(checkIfAccountsAreValid(fromAccountEntity,toAccountEntity)){
            val fromAccount = fromAccountEntity.get().toDomain()
            val toAccount = toAccountEntity.get().toDomain()
            fromAccount.debit(transferMoney.amount)
            toAccount.credit(transferMoney.amount)
            transactionTransferMoneyPort.transactionTransferMoney(fromAccount, toAccount)
        }else
            throw InvalidAccountException("invalid account")

    }

    private fun checkIfAccountsAreValid(fromAccountEntity: Optional<AccountEntity>, toAccountEntity: Optional<AccountEntity>)=
         fromAccountEntity.isPresent && toAccountEntity.isPresent
                && toAccountEntity.get().number != fromAccountEntity.get().number
}