package com.transferapp.moneytransferservice.adapter.`in`

import com.transferapp.moneytransferservice.adapter.out.dto.TransferMoneyDTORequest
import com.transferapp.moneytransferservice.adapter.utils.toTransaction
import com.transferapp.moneytransferservice.domain.usecases.TransferMoneyUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class TransferMoneyController(
    private val transferMoneyUseCase: TransferMoneyUseCase
) {

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun execute( @Valid @RequestBody transferMoneyDTORequest: TransferMoneyDTORequest){
        transferMoneyUseCase.execute(transferMoneyDTORequest.toTransaction())
    }

}