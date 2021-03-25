package com.transferapp.moneytransferservice.adapter.out

data class TransferMoneyDTORequest(
    val fromAccountId: String,
    val toAccountId: String,
    val amount: Double){
}