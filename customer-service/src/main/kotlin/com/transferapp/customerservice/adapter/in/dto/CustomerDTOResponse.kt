package com.transferapp.customerservice.adapter.`in`.dto


import com.transferapp.customerservice.domain.entity.RoleType

data class CustomerDTOResponse(val id:String?,
                               val name:String,
                               val email:String,
                               val documentId: String,
                               val status: Int?,
                               val role: RoleType)