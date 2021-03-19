package com.transferapp.customerservice.adapter.out.persistence.entity

import com.transferapp.customerservice.domain.entity.RoleType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("customer")
data class CustomerEntity(
        @Id
        val id: String?,
        val name:String,
        @Indexed(unique=true)
        val email:String,
        @Indexed(unique=true)
        val documentId: String,
        val status: Int,
        val role: RoleType
)