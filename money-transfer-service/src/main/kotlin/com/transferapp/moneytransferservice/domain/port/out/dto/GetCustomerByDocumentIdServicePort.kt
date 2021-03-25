package com.transferapp.moneytransferservice.domain.port.out.dto

import com.transferapp.moneytransferservice.adapter.out.CustomerDTOResponse

interface GetCustomerByDocumentIdServicePort {
    fun getByDocumentId(documentId: String): CustomerDTOResponse
}