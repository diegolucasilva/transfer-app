package com.transferapp.moneytransferservice.adapter.out.persistence

import com.transferapp.moneytransferservice.adapter.utils.toDomain
import com.transferapp.moneytransferservice.adapter.utils.toEntity
import com.transferapp.moneytransferservice.domain.entity.Transaction
import com.transferapp.moneytransferservice.domain.port.out.dto.SaveTransactionPort
import org.springframework.stereotype.Component

@Component
class TransactionAdapter(private val transactionRepository: TransactionRepository):SaveTransactionPort
{
    override fun save(transaction: Transaction): Transaction {
        val transactionEntity = transactionRepository.save(transaction.toEntity())
        return transactionEntity.toDomain()
    }
}