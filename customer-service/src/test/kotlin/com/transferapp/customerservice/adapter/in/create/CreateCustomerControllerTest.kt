package com.transferapp.customerservice.adapter.`in`.create

import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTORequest
import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTOResponse
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.entity.RoleType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
 class CreateCustomerControllerTest{

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate


    @Test
    fun `Test Create Customer Use Case - verify if customer is created`() {

        val request = givenCustomerRequest()

        val response = whenRequestIsMade(request)

        thenResponseShouldBeValid(response)
    }

    private fun thenResponseShouldBeValid(response: ResponseEntity<CustomerDTOResponse>?) {
        val customerDtoResponseExpected = CustomerDTOResponse(
                null,
                "John",
                "john@gmail.com",
                "45474",
                1,
                RoleType.USER
        )
        Assertions.assertEquals(response?.statusCode, HttpStatus.CREATED)
        Assertions.assertNotNull(response)
        Assertions.assertNotNull(response?.body?.id)
        Assertions.assertEquals(response?.body?.status, Customer.Status.ACTIVE.status)
        Assertions.assertEquals(response?.body?.name, customerDtoResponseExpected.name)
        Assertions.assertEquals(response?.body?.email, customerDtoResponseExpected.email)
        Assertions.assertEquals(response?.body?.documentId, customerDtoResponseExpected.documentId)
        Assertions.assertEquals(response?.body?.role, customerDtoResponseExpected.role)
    }

    private fun whenRequestIsMade(request: HttpEntity<CustomerDTORequest>): ResponseEntity<CustomerDTOResponse>? {
        return testRestTemplate.postForEntity("/customer", request, CustomerDTOResponse::class.java);
    }

    private fun givenCustomerRequest(): HttpEntity<CustomerDTORequest>{
        val customerDTORequest =  CustomerDTORequest(
                "John",
                "john@gmail.com",
                "45474",
                RoleType.USER
        )
        return HttpEntity<CustomerDTORequest>(customerDTORequest, HttpHeaders())
    }
}