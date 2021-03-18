package com.transferapp.customerservice.domain.usecases


import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerPort
import org.springframework.stereotype.Service

@Service
class GetCustomerUseCase(private val getCustomerPort: GetCustomerPort) {

    fun execute(id: String): Customer {
        return getCustomerPort.getById(id)
    }
}