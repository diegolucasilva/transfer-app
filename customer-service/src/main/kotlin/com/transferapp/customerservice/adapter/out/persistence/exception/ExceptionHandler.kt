package com.transferapp.customerservice.adapter.out.persistence.exception

import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Component
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
@Component
class ExceptionHandler(private val logger: Logger){

    @ExceptionHandler(Throwable::class)
    fun handleException(ex: Throwable): ResponseEntity<ErrorResponse> {
        logger.error("Exception caught in handleException :  {} ", ex)
        return when (ex) {
            is CustomerNotFoundException -> {
                getDefaultValidationResponseMessage(ex.fieldErrors, HttpStatus.NOT_FOUND)
            }
            is InvalidCustomerException -> {
                getDefaultValidationResponseMessage(ex.fieldErrors, HttpStatus.BAD_REQUEST)
            }
            is HttpMessageNotReadableException -> {
                getDefaultResponseMessage(ex.message, HttpStatus.BAD_REQUEST)
            }
            else -> {
                getDefaultResponseMessage(httpStatus =HttpStatus.SERVICE_UNAVAILABLE)
            }
        }
    }

    private fun getDefaultValidationResponseMessage(fieldErrors: MutableList<FieldError>? = null, httpStatus: HttpStatus) : ResponseEntity<ErrorResponse> {
        val errorList: MutableList<ErrorResponse.ErrorDescriptor> = mutableListOf()
        fieldErrors?.forEach {
            val error = ErrorResponse.ErrorDescriptor(parameterName= it.field, description = it.defaultMessage)
            errorList.add(error)
        }
        return ResponseEntity.status(httpStatus).body(ErrorResponse(errorList))
    }

    private fun getDefaultResponseMessage(message: String? = null, httpStatus: HttpStatus) : ResponseEntity<ErrorResponse> {
        val errorMessage = message ?: "Service unavailable"
        return ResponseEntity.status(httpStatus).body(ErrorResponse(description = errorMessage))
    }
}