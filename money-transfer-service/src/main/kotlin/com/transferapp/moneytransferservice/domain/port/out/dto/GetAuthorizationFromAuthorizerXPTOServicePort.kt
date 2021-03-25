package com.transferapp.moneytransferservice.domain.port.out.dto

import com.transferapp.moneytransferservice.adapter.out.dto.AuthorizerXPTOResponse
import com.transferapp.moneytransferservice.domain.entity.Transaction
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(value = "authorizer-xpto-service", url = "\${authorizer-XPTO.url}")
interface GetAuthorizationFromAuthorizerXPTOServicePort {

    @PostMapping(value = ["/8fafdd68-a090-496f-8c9a-3442cf30dae6"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAuthorization(@RequestBody transaction: Transaction): AuthorizerXPTOResponse
}