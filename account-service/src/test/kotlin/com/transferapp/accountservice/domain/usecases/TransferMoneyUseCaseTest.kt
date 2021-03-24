package com.transferapp.accountservice.domain.usecases

import com.nhaarman.mockitokotlin2.*
import com.transferapp.accountservice.adapter.out.persistence.entity.AccountEntity
import com.transferapp.accountservice.adapter.out.persistence.exception.InvalidAccountException
import com.transferapp.accountservice.domain.entity.Account
import com.transferapp.accountservice.domain.entity.TransferMoney
import com.transferapp.accountservice.domain.port.out.persistence.GetAccountByNumberPort
import com.transferapp.accountservice.domain.port.out.persistence.TransactionTransferMoneyPort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*


internal class TransferMoneyUseCaseTest{

    private val getAccountByNumberPort: GetAccountByNumberPort = mock()
    private val transactionTransferMoneyPort: TransactionTransferMoneyPort = mock()

    private val transferMoneyUseCase = TransferMoneyUseCase(getAccountByNumberPort, transactionTransferMoneyPort)


    @Test
    fun `When use case is executed should transfer money from an account to another account`() {
        val fromAccount = givenAccount(123, 150.10, Account.Status.ACTIVE.ordinal)
        val toAccount = givenAccount(999, 200.10, Account.Status.ACTIVE.ordinal)
        val moneyTransfer = TransferMoney(fromAccount.number,toAccount.number,100.00)

        whenUseCaseIsExecuted(moneyTransfer)
        verify(getAccountByNumberPort, times(1)).getByNumber(fromAccount.number)
        verify(getAccountByNumberPort, times(1)).getByNumber(toAccount.number)
        verify(transactionTransferMoneyPort, times(1)).transactionTransferMoney(any<Account>(), any<Account>())

    }

    @Test
    fun `When origin account has no funds should throws exception`() {
        val fromAccount = givenAccount(123, 90.00, Account.Status.ACTIVE.ordinal)
        val toAccount = givenAccount(999, 200.10, Account.Status.ACTIVE.ordinal)
        val moneyTransfer = TransferMoney(fromAccount.number,toAccount.number,100.00)

        val thrown = Assertions.assertThrows(InvalidAccountException::class.java) {
            whenUseCaseIsExecuted(moneyTransfer)

        }
        assertEquals( "account ${fromAccount.number} has no funds sufficient",thrown.messageError)
        verify(getAccountByNumberPort, times(1)).getByNumber(fromAccount.number)
        verify(getAccountByNumberPort, times(1)).getByNumber(toAccount.number)
        verify(transactionTransferMoneyPort, times(0)).transactionTransferMoney(any<Account>(), any<Account>())

    }

    @Test
    fun `When origin account is inactive should throws exception`() {
        val fromAccount = givenAccount(123, 100.00, Account.Status.INACTIVE.ordinal)
        val toAccount = givenAccount(999, 200.10, Account.Status.ACTIVE.ordinal)
        val moneyTransfer = TransferMoney(fromAccount.number,toAccount.number,100.00)

        val thrown = Assertions.assertThrows(InvalidAccountException::class.java) {
            whenUseCaseIsExecuted(moneyTransfer)

        }
        assertEquals( "account ${fromAccount.number} is inactive", thrown.messageError)
        verify(getAccountByNumberPort, times(1)).getByNumber(fromAccount.number)
        verify(getAccountByNumberPort, times(1)).getByNumber(toAccount.number)
        verify(transactionTransferMoneyPort, times(0)).transactionTransferMoney(any<Account>(), any<Account>())
    }

    @Test
    fun `When destination account is inactive should throws exception`() {
        val fromAccount = givenAccount(123, 100.00, Account.Status.ACTIVE.ordinal)
        val toAccount = givenAccount(999, 200.10, Account.Status.INACTIVE.ordinal)
        val moneyTransfer = TransferMoney(fromAccount.number,toAccount.number,100.00)

        val thrown = Assertions.assertThrows(InvalidAccountException::class.java) {
            whenUseCaseIsExecuted(moneyTransfer)

        }
        assertEquals( "account ${toAccount.number} is inactive", thrown.messageError)
        verify(getAccountByNumberPort, times(1)).getByNumber(fromAccount.number)
        verify(getAccountByNumberPort, times(1)).getByNumber(toAccount.number)
        verify(transactionTransferMoneyPort, times(0)).transactionTransferMoney(any<Account>(), any<Account>())
    }

    private fun whenUseCaseIsExecuted(transferMoney: TransferMoney){
        return transferMoneyUseCase.execute(transferMoney)
    }

    private fun givenAccount(accountNumber: Int, balance: Double, status: Int): AccountEntity{
        val accountEntity = AccountEntity(
            "11121-2121",
            accountNumber,
            status,
            "45474",
            balance
        )
        whenever(getAccountByNumberPort.getByNumber(accountNumber)).thenReturn(Optional.of(accountEntity))
        return accountEntity
    }


}