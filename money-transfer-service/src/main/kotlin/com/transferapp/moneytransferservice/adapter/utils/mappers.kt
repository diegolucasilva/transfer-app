package com.transferapp.moneytransferservice.adapter.utils

import com.transferapp.moneytransferservice.adapter.out.dto.TransferMoneyAccountServiceDTORequest
import com.transferapp.moneytransferservice.adapter.out.dto.TransferMoneyDTORequest
import com.transferapp.moneytransferservice.adapter.out.persistence.entity.TransactionEntity
import com.transferapp.moneytransferservice.domain.entity.Transaction

fun Transaction.toTransferMoneyDTORequest(): TransferMoneyDTORequest {
    val fromCustomerDTORequest  = TransferMoneyDTORequest.CustomerDTORequest(fromCustomer.accountId, fromCustomer.documentId)
    val toCustomerDTORequest  = TransferMoneyDTORequest.CustomerDTORequest(toCustomer.accountId, toCustomer.documentId)

    return TransferMoneyDTORequest(
        fromCustomer = fromCustomerDTORequest,
        toCustomer= toCustomerDTORequest,
        amount = amount
    )
}

fun Transaction.toEntity(): TransactionEntity {
    val fromCustomerEntity  = TransactionEntity.CustomerEntity(fromCustomer.accountId, fromCustomer.documentId)
    val toCustomerEntity   = TransactionEntity.CustomerEntity(toCustomer.accountId, toCustomer.documentId)

    return TransactionEntity(
        id = id,
        localDateTime= localDateTime,
        fromCustomer = fromCustomerEntity,
        toCustomer= toCustomerEntity,
        amount = amount,
        status = status
    )
}
fun Transaction.toTransferMoneyAccountServiceDTORequest(): TransferMoneyAccountServiceDTORequest {

    return TransferMoneyAccountServiceDTORequest(
        fromAccountId = fromCustomer.accountId,
        toAccountId= toCustomer.accountId,
        amount = amount,
    )
}




fun TransactionEntity.toDomain(): Transaction {
    val fromCustomer = Transaction.Customer(fromCustomer.accountId, fromCustomer.documentId)
    val toCustomer   = Transaction.Customer(toCustomer.accountId, toCustomer.documentId)
    return Transaction(
        id = id,
        localDateTime= localDateTime,
        fromCustomer = fromCustomer,
        toCustomer= toCustomer,
        amount = amount,
        status = status
    )
}

fun TransferMoneyDTORequest.toTransaction(): Transaction {
    val fromCustomer = Transaction.Customer(fromCustomer.accountId, fromCustomer.documentId)
    val toCustomer   = Transaction.Customer(toCustomer.accountId, toCustomer.documentId)
    return Transaction(
        fromCustomer = fromCustomer,
        toCustomer= toCustomer,
        amount = amount
    )
}