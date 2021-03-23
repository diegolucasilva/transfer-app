package com.transferapp.accountservice.adapter.out.persistence

import com.transferapp.accountservice.adapter.utls.toDomain
import com.transferapp.accountservice.adapter.utls.toEntity
import com.transferapp.accountservice.domain.entity.Account
import com.transferapp.accountservice.domain.port.out.persistence.SaveAccountPort
import org.springframework.stereotype.Component

@Component
class AccountAdapter(private val accountRepository: AccountRepository): SaveAccountPort{

    override fun save(account: Account): Account {
        val accountEntity =  account.toEntity()
        return accountRepository.save(accountEntity).toDomain()
    }
}