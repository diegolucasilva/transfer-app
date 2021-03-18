package com.transferapp.customerservice.adapter.`in`.get


import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTOResponse
import com.transferapp.customerservice.adapter.utils.toResponse
import com.transferapp.customerservice.domain.usecases.GetCustomerUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCustomerController(private val getCustomerUseCase: GetCustomerUseCase){

    @GetMapping("/customer/{id}")
    fun execute(@PathVariable("id") id:String): CustomerDTOResponse {
        val customer = getCustomerUseCase.execute(id)
        return customer.toResponse()
    }
}