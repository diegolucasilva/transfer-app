package com.transferapp.moneytransferservice.domain.port.out.dto

import com.transferapp.moneytransferservice.adapter.out.dto.TransferMoneyAccountServiceDTORequest
import com.transferapp.moneytransferservice.adapter.out.dto.TransferMoneyDTORequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(value = "account-service", url = "\${account-service.url}")
interface TransactionTransferMoneyServicePort {

    @PostMapping(value = ["/transfer"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun transactionTransferMoney(transferMoneyAccountServiceDTORequest: TransferMoneyAccountServiceDTORequest)

}