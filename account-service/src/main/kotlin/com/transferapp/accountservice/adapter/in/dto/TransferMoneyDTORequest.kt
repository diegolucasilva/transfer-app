package com.transferapp.accountservice.adapter.`in`.dto

import javax.validation.constraints.NotEmpty


data class TransferMoneyDTORequest(
    @NotEmpty
    val fromAccountId: Int,
    @NotEmpty
    val toAccountId: Int,
    @NotEmpty
    val amount: Double){
}