package com.transferapp.accountservice.domain.usecases

import com.transferapp.accountservice.domain.entity.Account
import com.transferapp.accountservice.domain.port.out.persistence.SaveAccountPort
import org.springframework.stereotype.Service

@Service
class CreateAccountUseCase(private val saveAccountPort: SaveAccountPort) {

    fun execute(account: Account): Account {
        return saveAccountPort.save(account)
    }
}