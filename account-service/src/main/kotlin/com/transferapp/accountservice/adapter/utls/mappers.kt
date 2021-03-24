package com.transferapp.accountservice.adapter.utls

import com.transferapp.accountservice.adapter.`in`.dto.AccountDTORequest
import com.transferapp.accountservice.adapter.`in`.dto.AccountDTOResponse
import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.domain.entity.Account

fun AccountDTORequest.toAccount(): Account {
    return Account(
        customerDocumentId = customerDocumentId,
        number= number
        )
}

fun Account.toResponse(): AccountDTOResponse {
    return AccountDTOResponse(
        id = id,
        number = number,
        customerDocumentId= customerDocumentId,
        status = status!!.ordinal)
}

fun Account.toEntity(): AccountEntity {
    val status = status!!.ordinal
    return AccountEntity(
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
