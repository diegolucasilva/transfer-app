package com.transferapp.moneytransferservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class MoneyTransferServiceApplication

fun main(args: Array<String>) {
	runApplication<MoneyTransferServiceApplication>(*args)
}
