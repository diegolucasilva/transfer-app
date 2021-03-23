package com.transferapp.accountservice.adapter.out.persistence

import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository: MongoRepository<AccountEntity, String> {


    fun findByNumber(number: Int): Optional<AccountEntity>
    fun findByCustomerDocumentId(documentId: String): Optional<AccountEntity>
}