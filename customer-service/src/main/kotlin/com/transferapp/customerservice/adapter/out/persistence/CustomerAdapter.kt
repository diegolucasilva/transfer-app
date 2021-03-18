package com.transferapp.customerservice.adapter.out.persistence


import com.transferapp.customerservice.adapter.utils.toDomain
import com.transferapp.customerservice.adapter.utils.toEntity
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerPort
import com.transferapp.customerservice.domain.port.out.persistence.SaveCustomerPort
import org.springframework.stereotype.Component

@Component
class CustomerAdapter(private val customerRepository: CustomerRepository): SaveCustomerPort, GetCustomerPort {

    override fun save(customer: Customer): Customer {
        val customerEntity = customer.toEntity()
        return customerRepository.save(customerEntity).toDomain()
    }


    override fun getById(id: String): Customer {
        val customerEntity = customerRepository.findById(id).orElseThrow{CustomerNotFoundException(id)}
        return customerEntity.toDomain()
    }

}