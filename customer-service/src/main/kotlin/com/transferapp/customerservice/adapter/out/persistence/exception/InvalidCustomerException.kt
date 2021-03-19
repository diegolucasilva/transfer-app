package com.transferapp.customerservice.adapter.out.persistence.exception

class InvalidCustomerException(val messageError: String): RuntimeException("Invalid customer " + messageError)