package com.transferapp.customerservice.domain.usecases


import com.transferapp.customerservice.adapter.out.persistence.exception.InvalidCustomerException
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerByDocumentIdPort
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerByEmailPort
import com.transferapp.customerservice.domain.port.out.persistence.SaveCustomerPort
import org.springframework.stereotype.Service
import org.springframework.validation.FieldError

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
        val fieldErrors: MutableList<FieldError> = mutableListOf()
        if (getCustomerByDocumentIdPort.getByDocumentId(documentId).isPresent)
            fieldErrors.add(FieldError("", "documentId", "customer documentID already exists"))
        if(getCustomerByEmailPort.getByEmail(email).isPresent)
            fieldErrors.add(FieldError("", "fromAccountId", "customer email already exists"))
        if(fieldErrors.isNotEmpty()){
            throw InvalidCustomerException(fieldErrors)
        }
    }
}