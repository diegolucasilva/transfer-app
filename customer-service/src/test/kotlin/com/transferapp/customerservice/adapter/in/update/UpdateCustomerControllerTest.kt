package com.transferapp.customerservice.adapter.`in`.update


import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTORequest
import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTOResponse
import com.transferapp.customerservice.adapter.out.persistence.CustomerRepository
import com.transferapp.customerservice.adapter.out.persistence.entity.CustomerEntity
import com.transferapp.customerservice.domain.entity.Customer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateCustomerControllerTest{
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Test
    fun `Test Update Customer Use Case - verify if customer is updated`() {
        val idCustomer = givenCustomerInDataBase()
        val request = givenCustomerRequest()

        val response = whenRequestIsMade(idCustomer, request)

        thenResponseShouldBeValid(response)
    }

    @Test
    fun `Test Update Customer Use Case - verify if CustomerNotFoundException is throws`() {

        val request = givenCustomerRequest()

        val response = whenInvalidRequestIsMade(request)

        Assertions.assertEquals(response?.statusCode, HttpStatus.NOT_FOUND)
    }


    private fun givenCustomerInDataBase(): String? {
        val customerEntity = CustomerEntity(
                id = null,
                name = "John",
                email = "john@gmail.com",
                documentId = "45474",
                status = 1
        )
        val customerSaved = customerRepository.save(customerEntity)
        return customerSaved.id
    }


    private fun thenResponseShouldBeValid(response: ResponseEntity<CustomerDTOResponse>?) {
        val customerDtoResponseExpected = CustomerDTOResponse(
                null,
                "John",
                "john@gmail.com",
                "45474",
                1
        )
        Assertions.assertEquals(response?.statusCode, HttpStatus.OK)
        Assertions.assertNotNull(response)
        Assertions.assertNotNull(response?.body?.id)
        Assertions.assertEquals(response?.body?.status, Customer.Status.ACTIVE.status)
        Assertions.assertEquals(response?.body?.name, customerDtoResponseExpected.name)
        Assertions.assertEquals(response?.body?.email, customerDtoResponseExpected.email)
        Assertions.assertEquals(response?.body?.documentId, customerDtoResponseExpected.documentId)
    }

    private fun whenInvalidRequestIsMade(request: HttpEntity<CustomerDTORequest>):  ResponseEntity<String>? {
        return testRestTemplate.exchange("/customer/1", HttpMethod.PUT, request, String::class.java)
    }

    private fun whenRequestIsMade(idCustomer: String?, request: HttpEntity<CustomerDTORequest>): ResponseEntity<CustomerDTOResponse>? {
         return testRestTemplate.exchange("/customer/${idCustomer}", HttpMethod.PUT, request, CustomerDTOResponse::class.java)
    }

    private fun givenCustomerRequest(): HttpEntity<CustomerDTORequest> {
        val customerDTORequest =  CustomerDTORequest(
                "John",
                "john@gmail.com",
                "45474"
        )
        return HttpEntity<CustomerDTORequest>(customerDTORequest, HttpHeaders())
    }




}
