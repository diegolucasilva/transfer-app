package com.transferapp.accountservice.domain.port.out.persistence

import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.domain.entity.Account

interface SaveAccountPort {
    fun save(account: Account): AccountEntity
}