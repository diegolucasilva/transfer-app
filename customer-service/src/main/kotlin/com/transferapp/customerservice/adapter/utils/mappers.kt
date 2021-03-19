package com.transferapp.customerservice.adapter.utils


import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTORequest
import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTOResponse
import com.transferapp.customerservice.adapter.out.persistence.entity.CustomerEntity
import com.transferapp.customerservice.domain.entity.Customer

fun CustomerEntity.toDomain(): Customer {
    val status: Customer.Status? = Customer.Status.values().getOrNull(status)
    return Customer(id, name, email, documentId, role, status)
}

fun Customer.toEntity(): CustomerEntity {
    val status = status!!.ordinal
    return CustomerEntity(id, name, email, documentId, status, role)
}

fun Customer.toResponse(): CustomerDTOResponse {
    return CustomerDTOResponse(id, name, email, documentId, status?.ordinal, role)
}

fun CustomerDTORequest.toCustomer(): Customer {
    return Customer(null, name, email, documentId, role)
}


