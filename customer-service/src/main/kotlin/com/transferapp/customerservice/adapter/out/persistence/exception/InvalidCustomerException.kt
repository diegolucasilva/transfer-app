package com.transferapp.customerservice.adapter.out.persistence.exception

import org.springframework.validation.FieldError

class InvalidCustomerException(val fieldErrors: MutableList<FieldError>): RuntimeException() {
    constructor(parameter_name: String = "", description: String) :
            this(mutableListOf(FieldError("", parameter_name, description)))
}