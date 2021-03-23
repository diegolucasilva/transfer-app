package com.transferapp.accountservice.domain.entity


data class Account(
    var id: String?  = null,
    val number: Int,
    val status: Status? = Status.ACTIVE,
    val customerDocumentId: String){

enum class Status(val status: Int) {
        INACTIVE(0),
        ACTIVE(1)
    }
}