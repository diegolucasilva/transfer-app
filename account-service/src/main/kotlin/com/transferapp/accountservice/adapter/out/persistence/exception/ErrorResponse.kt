package com.transferapp.accountservice.adapter.out.persistence.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class ErrorResponse(val errorMessages: List<ErrorDescriptor>) {
    constructor(parameter_name: String? = "", description: String? = "") :
            this(listOf(ErrorDescriptor(parameter_name, description)))

    data class ErrorDescriptor(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val parameterName: String?="",
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val description: String?=""
    )
}