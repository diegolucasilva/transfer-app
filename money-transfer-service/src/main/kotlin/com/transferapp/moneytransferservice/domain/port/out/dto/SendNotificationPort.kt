package com.transferapp.moneytransferservice.domain.port.out.dto


interface SendNotificationPort {
    fun send(message: String)
}