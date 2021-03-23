package com.transferapp.accountservice.adapter.out.persistence.entity

import org.springframework.data.annotation.Id

data class AccountEntity(
    @Id
    var id: String? = null,
    val number: Int,
    val status: Int,
    val customerDocumentId: String)