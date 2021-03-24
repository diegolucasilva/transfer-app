package com.transferapp.accountservice.adapter.out.persistence.exception

import org.springframework.validation.FieldError

class InvalidAccountException(val fieldErrors: MutableList<FieldError>): RuntimeException(){
    constructor(parameter_name: String="", description: String) :
            this(mutableListOf( FieldError("", parameter_name, description)))
}


