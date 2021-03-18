package com.transferapp.customerservice.domain.port.out.persistence

import com.transferapp.customerservice.domain.entity.Customer

interface SaveCustomerPort {
    fun save(customer: Customer): Customer
}