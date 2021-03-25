package com.transferapp.moneytransferservice.adapter.out.exception

import org.springframework.validation.FieldError

class RequestDeniedException(val fieldErrors: MutableList<FieldError>): RuntimeException() {
    constructor(parameter_name: String = "", description: String) :
            this(mutableListOf(FieldError("", parameter_name, description)))
}