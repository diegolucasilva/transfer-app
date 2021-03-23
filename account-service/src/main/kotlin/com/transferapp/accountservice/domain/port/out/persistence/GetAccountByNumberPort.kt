package com.transferapp.accountservice.domain.port.out.persistence

import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import java.util.*

interface GetAccountByNumberPort {
    fun getByNumber(number: Int): Optional<AccountEntity>
}