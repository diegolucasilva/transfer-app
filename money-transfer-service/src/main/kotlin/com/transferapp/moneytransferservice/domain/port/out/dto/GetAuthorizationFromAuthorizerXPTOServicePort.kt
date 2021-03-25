package com.transferapp.moneytransferservice.domain.port.out.dto

import com.transferapp.moneytransferservice.adapter.out.AuthorizerXPTOResponse
import com.transferapp.moneytransferservice.domain.entity.Transaction

interface GetAuthorizationFromAuthorizerXPTOServicePort {

    fun getAuthorization(transaction: Transaction): AuthorizerXPTOResponse
}