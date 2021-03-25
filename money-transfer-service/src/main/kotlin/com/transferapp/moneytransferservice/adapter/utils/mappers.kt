package com.transferapp.moneytransferservice.adapter.utils

import com.transferapp.moneytransferservice.adapter.out.dto.TransferMoneyDTORequest
import com.transferapp.moneytransferservice.adapter.out.persistence.entity.TransactionEntity
import com.transferapp.moneytransferservice.domain.entity.Transaction

fun Transaction.toTransferMoneyDTORequest(): TransferMoneyDTORequest {
    return TransferMoneyDTORequest(
        fromAccountId = fromAccountId,
        toAccountId= toAccountId,
        amount = amount
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        localDateTime= localDateTime,
        fromAccountId = fromAccountId,
        toAccountId= toAccountId,
        amount = amount,
        status = status
    )
}
fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        localDateTime= localDateTime,
        fromAccountId = fromAccountId,
        toAccountId= toAccountId,
        amount = amount,
        status = status
    )
}

fun TransferMoneyDTORequest.toTransaction(): Transaction {
    return Transaction(
        fromAccountId = fromAccountId,
        toAccountId= toAccountId,
        amount = amount
    )
}