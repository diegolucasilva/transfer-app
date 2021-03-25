package com.transferapp.moneytransferservice.adapter.out.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import feign.FeignException
import org.json.JSONObject
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Component
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
@Component
class ExceptionHandler(private val logger: Logger){

    @ExceptionHandler(Throwable::class)
    fun handleException(ex: Throwable): ResponseEntity<ErrorResponse> {
        logger.error("Exception caught in handleException :  {} ", ex)
        return when (ex) {
            is FeignException -> {
                getDefaultValidationResponseMessageFromFeign(ex)
            }
            is RequestDeniedException -> {
                getDefaultValidationResponseMessage(ex.fieldErrors, HttpStatus.FORBIDDEN)
            }
            is InvalidRequestException -> {
                getDefaultValidationResponseMessage(ex.fieldErrors, HttpStatus.BAD_REQUEST)
            }
            is MethodArgumentNotValidException -> {
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

    private fun getDefaultValidationResponseMessageFromFeign(exception: FeignException):  ResponseEntity<ErrorResponse> {
        val response = exception.message
        val jsonObject = JSONObject(response?.substring(response!!.indexOf("{"), response.lastIndexOf("}") + 1))

        val jsonArray = jsonObject.getJSONArray("error_messages")
        val errorList: MutableList<ErrorResponse.ErrorDescriptor> = mutableListOf()

        (0 until jsonArray.length()).forEach { index ->
            val jsonObject = jsonArray.getJSONObject(index)
            val error = ErrorResponse.ErrorDescriptor(parameterName= jsonObject.getString("parameterName"), description = jsonObject.getString("description"))
            errorList.add(error)
        }

        return ResponseEntity.status(HttpStatus.valueOf(exception.status())).body(ErrorResponse(errorList))

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