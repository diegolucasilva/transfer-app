package com.transferapp.customerservice.adapter.`in`.update


import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTORequest
import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTOResponse
import com.transferapp.customerservice.adapter.utils.toCustomer
import com.transferapp.customerservice.adapter.utils.toResponse
import com.transferapp.customerservice.domain.usecases.UpdateCustomerUseCase
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateCustomerController(private val updateCustomerUseCase: UpdateCustomerUseCase){

    @PutMapping("/customer/{id}")
    fun execute(@RequestBody customerDTORequest: CustomerDTORequest, @PathVariable("id") id:String): CustomerDTOResponse {
        return updateCustomerUseCase.execute(id,customerDTORequest.toCustomer()).toResponse()
    }
}