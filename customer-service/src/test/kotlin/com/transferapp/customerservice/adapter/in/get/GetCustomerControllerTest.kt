package com.transferapp.customerservice.adapter.`in`.get


import com.transferapp.customerservice.adapter.`in`.dto.CustomerDTOResponse
import com.transferapp.customerservice.adapter.out.persistence.CustomerRepository
import com.transferapp.customerservice.adapter.out.persistence.entity.CustomerEntity
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.entity.RoleType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetCustomerControllerTest{

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var customerRepository: CustomerRepository


    @Test
    fun `Test Get Customer Use Case - verify if customer is retrieved`() {
        val idCustomer = givenCustomerInDataBase()

        val response = whenRequestIsMade(idCustomer)

        thenResponseShouldBeValid(response)
    }

    private fun givenCustomerInDataBase(): String? {
        val customerEntity = CustomerEntity(
                id = null,
                name="John",
                email="john@gmail.com",
                documentId= "45474",
                status = 1,
            RoleType.USER
        )
        val customerSaved = customerRepository.save(customerEntity)
        return customerSaved.id
    }

    @Test
    fun `Test Get Customer Use Case - verify if CustomerNotFoundException is throws`() {
        val  response = whenInvalidRequestIsMade()

        Assertions.assertEquals(response?.statusCode, HttpStatus.NOT_FOUND)

    }

    private fun whenRequestIsMade(id: String?): ResponseEntity<CustomerDTOResponse>? {
        return testRestTemplate.getForEntity("/customer/${id}", CustomerDTOResponse::class.java);
    }
    private fun whenInvalidRequestIsMade(): ResponseEntity<String>? {
        return testRestTemplate.getForEntity("/customer/1", String::class.java);
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
        Assertions.assertEquals(response?.statusCode, HttpStatus.OK)
        Assertions.assertNotNull(response)
        Assertions.assertNotNull(response?.body?.id)
        Assertions.assertEquals(response?.body?.status, Customer.Status.ACTIVE.status)
        Assertions.assertEquals(response?.body?.name, customerDtoResponseExpected.name)
        Assertions.assertEquals(response?.body?.email, customerDtoResponseExpected.email)
        Assertions.assertEquals(response?.body?.documentId, customerDtoResponseExpected.documentId)
        Assertions.assertEquals(response?.body?.role, customerDtoResponseExpected.role)

    }


}