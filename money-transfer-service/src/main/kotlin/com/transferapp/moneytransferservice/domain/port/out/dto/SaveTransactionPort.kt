package com.transferapp.moneytransferservice.domain.port.out.dto

import com.transferapp.moneytransferservice.domain.entity.Transaction

interface SaveTransactionPort {

    fun save(transaction: Transaction): Transaction
}