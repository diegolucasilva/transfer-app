package com.transferapp.accountservice.domain.entity


data class MoneyTransfer(
    val fromAccountId: Int,
    val toAccountId: Int,
    val amount: Double){
}