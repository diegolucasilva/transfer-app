package com.transferapp.customerservice.domain.port.out.persistence

import com.transferapp.customerservice.adapter.out.persistence.entity.CustomerEntity
import java.util.*

interface GetCustomerByEmailPort {
    fun getByEmail(id: String): Optional<CustomerEntity>
}