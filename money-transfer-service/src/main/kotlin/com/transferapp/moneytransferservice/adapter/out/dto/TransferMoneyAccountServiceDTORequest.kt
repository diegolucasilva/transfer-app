package com.transferapp.moneytransferservice.adapter.out.dto

data class TransferMoneyAccountServiceDTORequest(
    val fromAccountId: String,
    val toAccountId: String,
    val amount: Double){
}
