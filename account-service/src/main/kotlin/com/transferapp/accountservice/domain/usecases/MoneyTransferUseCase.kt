package com.transferapp.accountservice.domain.usecases

import com.transferapp.accountservice.adapter.out.persistence.exception.InvalidAccountException
import com.transferapp.accountservice.adapter.utls.toDomain
import com.transferapp.accountservice.domain.entity.MoneyTransfer
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByNumberPort
import com.transferapp.accountservice.domain.port.out.persistence.TransactionTransferMoneyPort
import org.springframework.stereotype.Service

@Service
class MoneyTransferUseCase(
    private val getAccountByNumberPort: GetAccountByNumberPort,
    private val transactionTransferMoneyPort: TransactionTransferMoneyPort
) {

    fun execute(transferMoney: MoneyTransfer){
        val fromAccountEntity = getAccountByNumberPort.getByNumber(transferMoney.fromAccountId)
        val toAccountEntity = getAccountByNumberPort.getByNumber(transferMoney.toAccountId)

        if(fromAccountEntity.isPresent && toAccountEntity.isPresent){
            val fromAccount = fromAccountEntity.get().toDomain()
            val toAccount = toAccountEntity.get().toDomain()
            fromAccount.debit(transferMoney.amount)
            toAccount.credit(transferMoney.amount)
            transactionTransferMoneyPort.transactionTransferMoney(fromAccount, toAccount)
        }else
            throw InvalidAccountException("invalid account")

    }
}