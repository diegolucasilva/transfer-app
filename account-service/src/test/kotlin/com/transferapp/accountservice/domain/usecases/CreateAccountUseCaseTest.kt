package com.transferapp.accountservice.domain.usecases

import com.nhaarman.mockitokotlin2.*
import com.transferapp.accountservice.domain.port.out.persistence.SaveAccountPort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.adapter.utls.toDomain
import com.transferapp.accountservice.domain.entity.Account
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByDocumentIdPort
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByNumberPort
import java.util.*
import com.nhaarman.mockitokotlin2.any
import com.transferapp.accountservice.adapter.out.persistence.exception.InvalidAccountException


internal class CreateAccountUseCaseTest{

    private val saveAccountPort: SaveAccountPort = mock()
    private val getAccountByDocumentIdPortPort: GetAccountByDocumentIdPort = mock()
    private val getAccountByNumberPort: GetAccountByNumberPort = mock()

    private val createAccountUseCase = CreateAccountUseCase(saveAccountPort, getAccountByDocumentIdPortPort, getAccountByNumberPort)

    @Test
    fun `When use case is executed should create an account`() {
        givenAccount()

        val accountResponse = whenUseCaseIsExecuted()

        verify(saveAccountPort, times(1)).save(any<Account>())
        verify(getAccountByDocumentIdPortPort, times(1)).getByCustomerDocumentId(accountEntity.customerDocumentId)
        Assertions.assertEquals(accountResponse, accountEntity.toDomain())
    }

    @Test
    fun `Given a duplicate account number should return error`() {
        givenAccountInDatabase(accountEntity.customerDocumentId)

        Assertions.assertThrows(InvalidAccountException::class.java) {
            val accountResponse = whenUseCaseIsExecuted()
            verify(saveAccountPort, times(1)).save(accountEntity.toDomain())
            verify(getAccountByDocumentIdPortPort, times(1)).getByCustomerDocumentId(accountEntity.customerDocumentId)
            Assertions.assertEquals(accountResponse, accountEntity.toDomain())
        }
    }


    private fun whenUseCaseIsExecuted(): Account {
        return createAccountUseCase.execute(accountEntity.toDomain())
    }

    private fun givenAccount() {
        whenever(saveAccountPort.save(any<Account>())).thenReturn(accountEntity)
    }

    private fun givenAccountInDatabase(customerDocumentId: String) {
        whenever(saveAccountPort.save(accountEntity.toDomain())).thenReturn(accountEntity)
        whenever(getAccountByDocumentIdPortPort.getByCustomerDocumentId(customerDocumentId)).thenReturn(Optional.of(accountEntity))

    }

        private val accountEntity = AccountEntity(
            "11121-2121",
            45454,
            1,
            "45474"
        )
}