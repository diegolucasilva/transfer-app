package com.transferapp.moneytransferservice.adapter.dto


data class TransferMoneyRequest(
    val fromAccountId: String,
    val toAccountId: String,
    val amount: Double){
}