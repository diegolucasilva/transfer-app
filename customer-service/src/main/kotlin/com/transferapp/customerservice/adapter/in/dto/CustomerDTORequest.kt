package com.transferapp.customerservice.adapter.`in`.dto


import com.transferapp.customerservice.domain.entity.RoleType
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class CustomerDTORequest(
        @field:NotBlank
        val name:String,
        @field:NotBlank
        @field:Email
        val email:String,
        @field:Pattern(regexp = "^[0-9]{1,14}", message = "Document id (CPF/CNPJ) must have only positive number less than 14")
        val documentId: String,
        val role: RoleType
)

