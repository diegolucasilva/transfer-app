package com.transferapp.customerservice.adapter.`in`.dto


import com.transferapp.customerservice.domain.entity.RoleType
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

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

