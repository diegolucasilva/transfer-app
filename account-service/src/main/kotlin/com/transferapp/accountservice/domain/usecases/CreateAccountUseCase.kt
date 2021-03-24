package com.transferapp.accountservice.domain.usecases

import com.transferapp.accountservice.adapter.out.persistence.exception.InvalidAccountException
import com.transferapp.accountservice.adapter.utls.toDomain
import com.transferapp.accountservice.domain.entity.Account
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByDocumentIdPort
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByNumberPort
import com.transferapp.accountservice.domain.port.out.persistence.SaveAccountPort
import org.springframework.stereotype.Service

@Service
class CreateAccountUseCase(
    private val saveAccountPort: SaveAccountPort,
    private val getAccountByDocumentIdPort: GetAccountByDocumentIdPort,
    private val getAccountByNumberPort: GetAccountByNumberPort
) {

    fun execute(account: Account): Account {
        checkIfAccountAlreadyExists(account.customerDocumentId, account.number)
        return saveAccountPort.save(account).toDomain()
    }


    private fun checkIfAccountAlreadyExists(documentId: String, accountNumber: String){
        if (getAccountByDocumentIdPort.getByCustomerDocumentId(documentId).isPresent)
            throw InvalidAccountException(description ="account documentID already exists")
        else if(getAccountByNumberPort.getByNumber(accountNumber).isPresent)
            throw InvalidAccountException(description = "account number already exists")
    }

}