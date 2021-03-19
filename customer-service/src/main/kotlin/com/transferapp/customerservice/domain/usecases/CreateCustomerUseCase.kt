package com.transferapp.customerservice.domain.usecases


import com.transferapp.customerservice.adapter.out.persistence.exception.InvalidCustomerException
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerByDocumentIdPort
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerByEmailPort
import com.transferapp.customerservice.domain.port.out.persistence.SaveCustomerPort
import org.springframework.stereotype.Service

@Service
class CreateCustomerUseCase(
    private val saveCustomerPort: SaveCustomerPort,
    private val getCustomerByEmailPort: GetCustomerByEmailPort,
    private val getCustomerByDocumentIdPort: GetCustomerByDocumentIdPort,
    ) {

    fun execute(customer: Customer): Customer {
        checkIfCustomerAlreadyExists(customer.email, customer.documentId)
        return saveCustomerPort.save(customer)
    }

    private fun checkIfCustomerAlreadyExists(email: String, documentId: String){
        if (getCustomerByDocumentIdPort.getByDocumentId(documentId).isPresent)
            throw InvalidCustomerException("customer documentID already exists")
        else if(getCustomerByEmailPort.getByEmail(email).isPresent)
            throw InvalidCustomerException("customer email already exists")
    }
}