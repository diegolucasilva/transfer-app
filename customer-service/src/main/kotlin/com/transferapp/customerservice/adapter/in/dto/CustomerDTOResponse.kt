package com.transferapp.customerservice.adapter.`in`.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.transferapp.customerservice.domain.entity.RoleType

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class CustomerDTOResponse(val id:String?,
                               val name:String,
                               val email:String,
                               val documentId: String,
                               val status: Int?,
                               val role: RoleType)