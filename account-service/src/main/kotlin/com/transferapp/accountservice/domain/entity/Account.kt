package com.transferapp.accountservice.domain.entity

import com.transferapp.accountservice.adapter.out.persistence.exception.InvalidAccountException


data class Account(
    var id: String?  = null,
    val number: String,
    val status: Status? = Status.ACTIVE,
    var balance: Double = 0.0,
    val customerDocumentId: String){


    fun debit(amount: Double) {
        this.isActive()
        if(this.balance!! >= amount) {
               this.balance -= amount
        }else
            throw InvalidAccountException(description ="account ${this.number} has no funds sufficient")
    }

    fun credit(amount: Double) {
        this.isActive()
        this.balance += amount
    }

    private fun isActive(){
        if(this.status == Status.INACTIVE)
            throw InvalidAccountException(description = "account ${this.number} is inactive")
    }

        enum class Status(val status: Int) {
            INACTIVE(0),
            ACTIVE(1)
        }
}
