package com.transferapp.accountservice.domain.port.out.persistence

import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import java.util.*

interface GetAccountByDocumentIdPort {
    fun getByCustomerDocumentId(id: String): Optional<AccountEntity>
}