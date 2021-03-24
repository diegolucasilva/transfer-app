package com.transferapp.accountservice.adapter.`in`.dto


data class AccountDTOResponse(
    val id: String?,
    val number: String,
    val customerDocumentId: String,
    val status: Int,
    val balance: Double
)