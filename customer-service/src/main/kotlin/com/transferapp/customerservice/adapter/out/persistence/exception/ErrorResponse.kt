package com.transferapp.customerservice.adapter.out.persistence.exception

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class ErrorResponse(val errorMessages: List<ErrorDescriptor>) {
    constructor(code: Long? = 0, description: String, parameter_name: String? = "", doc_url: String? = "", errors: String? = "") :
            this(listOf(ErrorDescriptor(code, description, parameter_name, doc_url, errors)))

    data class ErrorDescriptor(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val code: Long?,
        val description: String,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val parameterName: String?,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val docUrl: String?,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val errors: String?

    )
}