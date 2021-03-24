package com.transferapp.accountservice.domain.entity


data class TransferMoney(
    val fromAccountId: String,
    val toAccountId: String,
    val amount: Double){
}