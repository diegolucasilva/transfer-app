package com.transferapp.accountservice.adapter.`in`.dto

import javax.validation.constraints.Digits
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class AccountDTORequest(
    @field:NotBlank
    @field:Pattern(regexp = "^[0-9]{1,14}", message = "Document id (CPF/CNPJ) must have only positive number less than 14")
    val customerDocumentId: String,
    @field:Pattern(regexp = "^[0-9]{1,10}", message = "Account number must have only positive number less than 10")
    val number: String,
    @field:Digits(integer = 18, fraction = 2, message = "balance value is invalid")
    val balance: Double
    )