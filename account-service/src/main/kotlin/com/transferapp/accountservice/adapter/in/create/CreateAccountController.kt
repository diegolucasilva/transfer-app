package com.transferapp.accountservice.adapter.`in`.create

import com.transferapp.accountservice.adapter.`in`.dto.AccountDTORequest
import com.transferapp.accountservice.adapter.`in`.dto.AccountDTOResponse
import com.transferapp.accountservice.adapter.utls.toAccount
import com.transferapp.accountservice.adapter.utls.toResponse
import com.transferapp.accountservice.domain.usecases.CreateAccountUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class CreateAccountController(private val createAccountUseCase: CreateAccountUseCase) {

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute( @Valid @RequestBody accountDTORequest: AccountDTORequest): AccountDTOResponse {
        val account = createAccountUseCase.execute(accountDTORequest.toAccount())
        return account.toResponse()
    }
}