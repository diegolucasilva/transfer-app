package com.transferapp.accountservice.adapter.`in`.dto

import javax.validation.constraints.Digits
import javax.validation.constraints.Pattern


data class TransferMoneyDTORequest(
    @field:Pattern(regexp = "^[0-9]{1,14}", message = "Document id (CPF/CNPJ) must have only positive number less than 14")
    val fromAccountId: String,
    @field:Pattern(regexp = "^[0-9]{1,14}", message = "Document id (CPF/CNPJ) must have only positive number less than 14")
    val toAccountId: String,
    @field:Digits(integer = 18, fraction = 2, message = "balance value is invalid")
    val amount: Double){
}