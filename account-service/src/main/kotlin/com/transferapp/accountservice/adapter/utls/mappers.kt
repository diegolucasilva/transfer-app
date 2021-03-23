package com.transferapp.accountservice.adapter.utls

import com.transferapp.accountservice.adapter.`in`.dto.AccountDTORequest
import com.transferapp.accountservice.adapter.`in`.dto.AccountDTOResponse
import com.transferapp.accountservice.domain.entity.Account

fun AccountDTORequest.toAccount(): Account {
    return Account(customerDocumentId)
}

fun Account.toResponse(): AccountDTOResponse {
    return AccountDTOResponse(id, number, customerDocumentId)
}
