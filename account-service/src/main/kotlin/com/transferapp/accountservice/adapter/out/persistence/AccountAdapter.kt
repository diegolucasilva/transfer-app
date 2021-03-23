package com.transferapp.accountservice.adapter.out.persistence

import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.adapter.utls.toEntity
import com.transferapp.accountservice.domain.entity.Account
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByDocumentIdPort
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByNumberPort
import com.transferapp.accountservice.domain.port.out.persistence.SaveAccountPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class AccountAdapter(private val accountRepository: AccountRepository): SaveAccountPort, GetAccountByDocumentIdPort,
    GetAccountByNumberPort {

    override fun save(account: Account): AccountEntity {
        val accountEntity =  account.toEntity()
        return accountRepository.save(accountEntity)
    }

    override fun getByCustomerDocumentId(id: String): Optional<AccountEntity> {
        return accountRepository.findByCustomerDocumentId(id)
    }

    override fun getByNumber(number: Int): Optional<AccountEntity> {
        return accountRepository.findByNumber(number)
    }


}