package com.transferapp.moneytransferservice.adapter.out.dto

data class TransferMoneyDTORequest(
    val fromCustomer: CustomerDTORequest,
    val toCustomer: CustomerDTORequest,
    val amount: Double){

    data class CustomerDTORequest(
        val accountId: String,
        val documentId: String
    )
}
