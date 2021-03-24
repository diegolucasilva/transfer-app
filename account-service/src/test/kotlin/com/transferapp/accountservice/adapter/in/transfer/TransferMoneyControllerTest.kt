package com.transferapp.accountservice.adapter.`in`.transfer

import com.transferapp.accountservice.adapter.`in`.dto.AccountDTORequest
import com.transferapp.accountservice.adapter.`in`.dto.AccountDTOResponse
import com.transferapp.accountservice.adapter.`in`.dto.TransferMoneyDTORequest
import com.transferapp.accountservice.adapter.out.persistence.AccountRepository
import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.domain.entity.Account
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
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
internal class TransferMoneyControllerTest{

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate
    @Autowired
    lateinit var accountRepository: AccountRepository

    @BeforeEach
    fun cleanMongo(){
        accountRepository.deleteAll()
    }

    @Test
    fun `When request is made should transfer money from an account to another`() {
        val fromAccount = givenAccountInDataBase(123,"4444", 100.00, Account.Status.ACTIVE.ordinal)
        val toAccount = givenAccountInDataBase(124,"4444", 100.00, Account.Status.ACTIVE.ordinal)

        val request = givenTransferMoneyRequest(fromAccount.number,toAccount.number, 100.00)

        val response = whenRequestIsMade(request)

        Assertions.assertEquals(response?.statusCode, HttpStatus.NO_CONTENT)
    }

    @Test
    fun `When origin account has no funds the response should be bad request`() {
        val fromAccount = givenAccountInDataBase(123,"4444", 99.00, Account.Status.ACTIVE.ordinal)
        val toAccount = givenAccountInDataBase(124,"4444", 100.00, Account.Status.ACTIVE.ordinal)

        val request = givenTransferMoneyRequest(fromAccount.number,toAccount.number, 100.00)

        val response = whenRequestIsMade(request)

        Assertions.assertEquals(response?.statusCode, HttpStatus.BAD_REQUEST)
        Assertions.assertEquals("account ${fromAccount.number} has no funds sufficient",
            response?.body?.let { extractDescriptionErrorMessage(it) })
    }

    @Test
    fun `When origin account is inactive the response should be bad request`() {
        val fromAccount = givenAccountInDataBase(123,"4444", 99.00, Account.Status.INACTIVE.ordinal)
        val toAccount = givenAccountInDataBase(124,"4444", 100.00, Account.Status.ACTIVE.ordinal)

        val request = givenTransferMoneyRequest(fromAccount.number,toAccount.number, 100.00)

        val response = whenRequestIsMade(request)

        Assertions.assertEquals(response?.statusCode, HttpStatus.BAD_REQUEST)
        Assertions.assertEquals("account ${fromAccount.number} is inactive",
            response?.body?.let { extractDescriptionErrorMessage(it) })
    }

    @Test
    fun `When destination account is inactive the response should be bad request`() {
        val fromAccount = givenAccountInDataBase(123,"4444", 100.00, Account.Status.ACTIVE.ordinal)
        val toAccount = givenAccountInDataBase(124,"4444", 100.00, Account.Status.INACTIVE.ordinal)

        val request = givenTransferMoneyRequest(fromAccount.number,toAccount.number, 100.00)

        val response = whenRequestIsMade(request)

        Assertions.assertEquals(response?.statusCode, HttpStatus.BAD_REQUEST)
        Assertions.assertEquals("account ${toAccount.number} is inactive",
            response?.body?.let { extractDescriptionErrorMessage(it) })
    }


    private fun whenRequestIsMade(request: HttpEntity<TransferMoneyDTORequest>): ResponseEntity<String>? {
        return testRestTemplate.postForEntity("/transfer", request, String::class.java)
    }

    private fun givenTransferMoneyRequest(fromAccount: Int, toAccount: Int, amount: Double): HttpEntity<TransferMoneyDTORequest> {
        val transferMoneyDTORequest =  TransferMoneyDTORequest(
            fromAccount,
            toAccount,
            amount
        )
        return HttpEntity<TransferMoneyDTORequest>(transferMoneyDTORequest, HttpHeaders())
    }

    private fun givenAccountInDataBase(number: Int, customerDocumentId: String, balance: Double,status: Int): AccountEntity{
        val accountEntity = AccountEntity(
            number = number,
            status = status,
            customerDocumentId = customerDocumentId,
            balance= balance
        )
        return accountRepository.save(accountEntity)
    }

    private fun extractDescriptionErrorMessage(response: String): String{
        val jsonObject = JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1))
        val jsonArray = jsonObject.getJSONArray("error_messages")
        return jsonArray.getJSONObject(0).getString("description")
    }
}