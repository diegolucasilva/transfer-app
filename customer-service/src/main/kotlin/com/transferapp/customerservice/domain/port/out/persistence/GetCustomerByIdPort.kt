package com.transferapp.customerservice.domain.port.out.persistence

import com.transferapp.customerservice.domain.entity.Customer

interface GetCustomerByIdPort {
    fun getById(id: String): Customer
}