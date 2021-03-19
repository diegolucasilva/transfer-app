package com.transferapp.customerservice.adapter.out.persistence


import com.transferapp.customerservice.adapter.out.persistence.entity.CustomerEntity
import com.transferapp.customerservice.adapter.utils.toDomain
import com.transferapp.customerservice.adapter.utils.toEntity
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerByDocumentIdPort
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerByEmailPort
import com.transferapp.customerservice.domain.port.out.persistence.SaveCustomerPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerByAdapter(private val customerRepository: CustomerRepository):
    SaveCustomerPort,
    GetCustomerByDocumentIdPort,
    GetCustomerByEmailPort {

    override fun save(customer: Customer): Customer {
        val customerEntity = customer.toEntity()
        return customerRepository.save(customerEntity).toDomain()
    }

    override fun getByDocumentId(documentId: String): Optional<CustomerEntity> {
        return customerRepository.findByDocumentId(documentId);
    }

    override fun getByEmail(documentId: String): Optional<CustomerEntity> {
        return customerRepository.findByDocumentId(documentId);
    }

}