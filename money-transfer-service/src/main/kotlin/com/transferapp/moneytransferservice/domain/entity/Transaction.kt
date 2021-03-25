package com.transferapp.moneytransferservice.domain.entity

import java.time.LocalDateTime

data class Transaction(
    var id:String?=null,
    val localDateTime: LocalDateTime?= LocalDateTime.now(),
    val fromCustomer: Customer,
    val toCustomer: Customer,
    val amount: Double,
    var status:Status?=Status.PENDING
    ) {

    fun confirm() {
        this.status = Status.OK
    }

    fun denied() {
        this.status = Status.DENIED
    }

    data class Customer(
        val accountId: String,
        val documentId: String
    )
    enum class Status(val status: Int) {
        PENDING(0),
        OK(1),
        REVERSED(2),
        DENIED(3)
    }
}
