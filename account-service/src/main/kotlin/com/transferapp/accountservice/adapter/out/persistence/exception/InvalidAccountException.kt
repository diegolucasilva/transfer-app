package com.transferapp.accountservice.adapter.out.persistence.exception

class InvalidAccountException(val messageError: String): RuntimeException("Invalid account " + messageError)