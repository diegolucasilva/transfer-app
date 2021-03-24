package com.transferapp.accountservice.domain.entity

import com.transferapp.accountservice.adapter.out.persistence.exception.InvalidAccountException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random.Default.nextDouble

internal class AccountTest{

    @Test
    fun `When some amount is debited it must be recalculated`() {
        val balance = 20000.00
        val amount = nextDouble(0.00, 19999.00)
        val account = givenAccount(balance, Account.Status.ACTIVE)

        account.debit(amount)

        Assertions.assertEquals(account.balance, (balance.minus(amount)))
    }

    @Test
    fun `When account has no sufficient funds should throws exception`() {
        val balance = 10.00
        val amount = nextDouble(11.00, 19999.00)
        val account = givenAccount(balance, Account.Status.ACTIVE)

        val thrown = Assertions.assertThrows(InvalidAccountException::class.java) {
            account.debit(amount)
        }
        Assertions.assertEquals("account ${account.number} has no funds sufficient", thrown.messageError)
        Assertions.assertEquals(account.balance, balance)
    }

    @Test
    fun `When some amount is credited it must be recalculated`() {
        val balance = nextDouble(0.00, 19999.00)
        val amount = nextDouble(0.00, 19999.00)
        val account = givenAccount(balance, Account.Status.ACTIVE)

        account.credit(amount)
        Assertions.assertEquals(account.balance, (balance.plus(amount)))
    }

    @Test
    fun `When account is inactive should throws exception`() {
        val balance = nextDouble(0.00, 19999.00)
        val amount = nextDouble(0.00, 19999.00)
        val account = givenAccount(balance, Account.Status.INACTIVE)

        val thrown = Assertions.assertThrows(InvalidAccountException::class.java) {
            account.credit(amount)
        }
        Assertions.assertEquals("account ${account.number} is inactive", thrown.messageError)
        Assertions.assertEquals(account.balance, balance)
    }

    private fun givenAccount(balance: Double, status:  Account.Status): Account {
       return Account(
            "11121-2121",
            1212,
            status,
            balance,
       "23132"
        )
    }
}