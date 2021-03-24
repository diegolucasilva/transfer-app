package com.transferapp.accountservice.adapter.`in`.transfer

import com.transferapp.accountservice.adapter.`in`.dto.TransferMoneyDTORequest
import com.transferapp.accountservice.adapter.utls.toDomain
import com.transferapp.accountservice.domain.usecases.TransferMoneyUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class TransferMoneyController(private val transferMoneyUseCase: TransferMoneyUseCase) {

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun execute( @Valid @RequestBody transferMoneyDTORequest: TransferMoneyDTORequest){
       transferMoneyUseCase.execute(transferMoneyDTORequest.toDomain())
    }
}