package com.transferapp.accountservice.adapter.`in`.dto

import javax.validation.constraints.NotBlank

data class AccountDTORequest(
    @field:NotBlank
    val customerDocumentId: String
    )
