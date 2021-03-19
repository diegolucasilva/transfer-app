package com.transferapp.customerservice.domain.port.out.persistence

import com.transferapp.customerservice.adapter.out.persistence.entity.CustomerEntity
import java.util.*

interface GetCustomerByDocumentIdPort {
    fun getByDocumentId(documentId: String): Optional<CustomerEntity>
}