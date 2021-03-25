package com.transferapp.moneytransferservice.adapter.out.persistence.entity

import com.transferapp.moneytransferservice.domain.entity.Transaction
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("transaction")
data class TransactionEntity(
    @Id
    val id:String?,
    val localDateTime: LocalDateTime?,
    val fromAccountId: String,
    val toAccountId: String,
    val amount: Double,
    var status: Transaction.Status?)