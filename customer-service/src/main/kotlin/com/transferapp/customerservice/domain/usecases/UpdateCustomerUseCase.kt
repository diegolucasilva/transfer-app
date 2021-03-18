package com.transferapp.customerservice.domain.usecases


import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerPort
import com.transferapp.customerservice.domain.port.out.persistence.SaveCustomerPort
import org.springframework.stereotype.Service

@Service
class UpdateCustomerUseCase(private val saveCustomerPort: SaveCustomerPort, private val getCustomerPort: GetCustomerPort) {

    fun execute(id: String, customer: Customer): Customer{
        getCustomerPort.getById(id)
        customer.id = id
        return saveCustomerPort.save(customer)
    }
}