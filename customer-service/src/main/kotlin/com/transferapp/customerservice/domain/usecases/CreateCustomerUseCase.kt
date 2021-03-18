package com.transferapp.customerservice.domain.usecases


import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.SaveCustomerPort
import org.springframework.stereotype.Service

@Service
class CreateCustomerUseCase(private val saveCustomerPort: SaveCustomerPort) {


    fun execute(customer: Customer): Customer {
        return saveCustomerPort.save(customer)
    }
}