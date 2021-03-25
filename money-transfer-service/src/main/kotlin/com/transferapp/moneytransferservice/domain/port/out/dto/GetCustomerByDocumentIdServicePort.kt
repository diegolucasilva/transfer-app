package com.transferapp.moneytransferservice.domain.port.out.dto

import com.transferapp.moneytransferservice.adapter.out.dto.CustomerDTOResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(value = "customer-service", url = "\${customer-service.url}")
interface GetCustomerByDocumentIdServicePort {

    @GetMapping(value = ["/customer/{documentId}"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByDocumentId(@PathVariable documentId: String): CustomerDTOResponse
}