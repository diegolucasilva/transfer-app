package com.transferapp.accountservice.adapter.out.persistence.exception

class AccountNotFoundException(val id: String): RuntimeException("Could not find account " + id)