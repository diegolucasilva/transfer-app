package com.transferapp.customerservice.domain.entity


data class Customer(
        var id:String?,
        val name:String,
        val email:String,
        val documentId: String,
        var status: Status?=Status.ACTIVE) {

    fun inactive() {
        status = Status.INACTIVE
    }

    enum class Status(val status: Int) {
        INACTIVE(0),
        ACTIVE(1)
    }
}

