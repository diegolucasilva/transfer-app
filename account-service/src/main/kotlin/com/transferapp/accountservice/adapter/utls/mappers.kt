package com.transferapp.accountservice.adapter.utls

import com.transferapp.accountservice.adapter.`in`.dto.AccountDTORequest
import com.transferapp.accountservice.adapter.`in`.dto.AccountDTOResponse
import com.transferapp.accountservice.adapter.`in`.dto.TransferMoneyDTORequest
import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.domain.entity.Account
import com.transferapp.accountservice.domain.entity.TransferMoney

fun AccountDTORequest.toAccount(): Account {
    return Account(
        customerDocumentId = customerDocumentId,
        number= number,
        balance = balance
        )
}

fun Account.toResponse(): AccountDTOResponse {
    return AccountDTOResponse(
        id = id,
        number = number,
        customerDocumentId= customerDocumentId,
        status = status!!.ordinal,
        balance = balance)
}

fun Account.toEntity(): AccountEntity {
    val status = status!!.ordinal
    return AccountEntity(
        id = id,
        number = number,
        status = status,
        customerDocumentId = customerDocumentId,
        balance = balance
       )
}

fun AccountEntity.toDomain(): Account {
    val status: Account.Status? = Account.Status.values().getOrNull(status)
    return Account(
        id = id,
        number = number,
        status = status,
        customerDocumentId = customerDocumentId,
        balance = balance
    )
}
fun TransferMoneyDTORequest.toDomain(): TransferMoney {
    return TransferMoney(
        fromAccountId = fromAccountId,
        toAccountId = toAccountId,
        amount = amount)
    }
