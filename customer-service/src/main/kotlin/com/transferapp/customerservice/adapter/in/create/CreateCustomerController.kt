package com.transferapp.customerservice.adapter.`in`.create


import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTORequest
import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTOResponse
import com.transferapp.customerservice.adapter.utils.toCustomer
import com.transferapp.customerservice.adapter.utils.toResponse
import com.transferapp.customerservice.domain.usecases.CreateCustomerUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class CreateCustomerController(private val createCustomerUseCase: CreateCustomerUseCase){

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(@Valid @RequestBody customerDTORequest: CustomerDTORequest): CustomerDTOResponse {
            val customer = createCustomerUseCase.execute(customerDTORequest.toCustomer())
        return customer.toResponse()
    }
}