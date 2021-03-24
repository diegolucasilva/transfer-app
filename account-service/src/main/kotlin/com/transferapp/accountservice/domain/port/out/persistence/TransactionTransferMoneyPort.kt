package com.transferapp.accountservice.domain.port.out.persistence

import com.transferapp.accountservice.domain.entity.Account

interface TransactionTransferMoneyPort {

    fun transactionTransferMoney(fromAccount: Account, toAccount: Account)


}