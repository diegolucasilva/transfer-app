package com.transferapp.customerservice.adapter.`in`.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.transferapp.customerservice.domain.entity.RoleType
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class CustomerDTORequest(
        @field:NotBlank
        val name:String,
        @field:NotBlank
        @field:Email
        val email:String,
        @field:NotBlank
        val documentId: String,
        val role: RoleType
)

