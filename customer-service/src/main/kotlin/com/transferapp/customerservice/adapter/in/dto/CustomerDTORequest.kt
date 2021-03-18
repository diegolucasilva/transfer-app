package com.transferapp.customerservice.adapter.`in`.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CustomerDTORequest(
        @field:NotBlank
        val name:String,
        @field:NotBlank
        @field:Email
        val email:String,
        @field:NotBlank
        val documentId: String)

