package com.transferapp.customerservice.adapter.out.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("customer")
data class CustomerEntity(
        @Id
        val id: String?,
        val name:String,
        val email:String,
        val documentId: String,
        val status: Int
)