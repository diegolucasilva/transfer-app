package com.transferapp.accountservice.domain.entity

import java.util.*

class Account{
    var id: String?  = null
    val number: Int
    val status: Status?
    val customerDocumentId: String

    constructor(customerDocumentId: String){
        this.customerDocumentId = customerDocumentId
        this.number = Random().nextInt(899999) + 100000
        this.status = Status.ACTIVE
    }

    constructor(id: String?, number:Int, status: Status?, customerDocumentId: String){
        this.id = id
        this.customerDocumentId = customerDocumentId
        this.number = number
        this.status = status
    }


    enum class Status(val status: Int) {
        INACTIVE(0),
        ACTIVE(1)
    }
}