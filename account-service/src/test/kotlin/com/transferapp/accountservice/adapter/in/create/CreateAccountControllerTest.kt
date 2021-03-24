package com.transferapp.accountservice.adapter.`in`.create

import com.transferapp.accountservice.adapter.`in`.dto.AccountDTORequest
import com.transferapp.accountservice.adapter.`in`.dto.AccountDTOResponse
import com.transferapp.accountservice.domain.entity.Account
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
internal class CreateAccountControllerTest{

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun `Test Create Account Use Case - verify if account is created`() {

        val request = givenAccountRequest()

        val response = whenRequestIsMade(request)

        thenResponseShouldBeValid(response)
    }

    private fun whenRequestIsMade(request: HttpEntity<AccountDTORequest>): ResponseEntity<AccountDTOResponse>? {
        return testRestTemplate.postForEntity("/account", request, AccountDTOResponse::class.java)
    }

    private fun givenAccountRequest(): HttpEntity<AccountDTORequest> {
        val accountDTORequest =  AccountDTORequest(
            "4545445",
            4545111,
            100.00
        )
        return HttpEntity<AccountDTORequest>(accountDTORequest, HttpHeaders())
    }

    private fun thenResponseShouldBeValid(response: ResponseEntity<AccountDTOResponse>?) {
        Assertions.assertEquals(response?.statusCode, HttpStatus.CREATED)
        Assertions.assertNotNull(response)
        Assertions.assertNotNull(response?.body?.id)
        Assertions.assertEquals(response?.body?.status, Account.Status.ACTIVE.ordinal)
        Assertions.assertEquals(response?.body?.customerDocumentId, "4545445")
        Assertions.assertEquals(response?.body?.number,4545111)
    }
}