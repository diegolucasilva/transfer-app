package com.transferapp.customerservice.domain.port.out.persistence

import com.transferapp.customerservice.domain.entity.Customer

interface GetCustomerPort {
    fun getById(id: String): Customer
}