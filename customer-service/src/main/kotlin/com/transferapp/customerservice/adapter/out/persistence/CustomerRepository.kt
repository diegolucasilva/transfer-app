package com.transferapp.customerservice.adapter.out.persistence

import com.transferapp.customerservice.adapter.out.persistence.entity.CustomerEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository: MongoRepository<CustomerEntity,String>{

    fun findByEmail(email: String): Optional<CustomerEntity>
    fun findByDocumentId(documentId: String): Optional<CustomerEntity>

}