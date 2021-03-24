package com.transferapp.accountservice.domain.entity


data class TransferMoney(
    val fromAccountId: Int,
    val toAccountId: Int,
    val amount: Double){
}