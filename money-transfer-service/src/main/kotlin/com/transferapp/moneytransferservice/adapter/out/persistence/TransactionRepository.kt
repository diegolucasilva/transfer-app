package com.transferapp.moneytransferservice.adapter.out.persistence

import com.transferapp.moneytransferservice.adapter.out.persistence.entity.TransactionEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: MongoRepository<TransactionEntity, String> {
}