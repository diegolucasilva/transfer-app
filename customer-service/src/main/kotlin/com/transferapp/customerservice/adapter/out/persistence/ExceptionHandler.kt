package com.transferapp.customerservice.adapter.out.persistence

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = [(CustomerNotFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun customerNotFoundHandler(ex: CustomerNotFoundException): String? {
        return ex.message
    }
}