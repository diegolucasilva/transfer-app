package com.transferapp.moneytransferservice.adapter.out.dto

data class TransferMoneyDTORequest(
    val fromAccountId: String,
    val toAccountId: String,
    val amount: Double){
}