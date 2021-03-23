package com.transferapp.accountservice.adapter.`in`.dto


data class AccountDTOResponse(
    val id: String?,
    val number: Int,
    val customerDocumentId: String,
    val status: Int,
)
