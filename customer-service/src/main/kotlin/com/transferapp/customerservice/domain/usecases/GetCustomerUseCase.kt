package com.transferapp.customerservice.domain.usecases

import com.transferapp.customerservice.adapter.out.persistence.exception.CustomerNotFoundException
import com.transferapp.customerservice.adapter.utils.toDomain
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerByDocumentIdPort
import org.springframework.stereotype.Service

@Service
class GetCustomerUseCase(private val getCustomerByDocumentIdPort: GetCustomerByDocumentIdPort) {

    fun execute(documentId: String): Customer {
        val customerEntity =  getCustomerByDocumentIdPort.getByDocumentId(documentId).
        orElseThrow{ CustomerNotFoundException(documentId) }
        return customerEntity.toDomain()
    }

}