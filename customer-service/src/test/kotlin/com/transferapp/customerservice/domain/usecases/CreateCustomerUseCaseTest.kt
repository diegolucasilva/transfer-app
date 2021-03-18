package com.transferapp.customerservice.domain.usecases

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.SaveCustomerPort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class CreateCustomerUseCaseTest{

    private val saveCustomerPort: SaveCustomerPort = mock()
    private val createCustomerUseCase = CreateCustomerUseCase(saveCustomerPort)

    @Test
    fun `Test Create Customer Use Case - verify if customer is created`() {
        givenCustomer()

        val customerResponse = whenUseCaseIsExecuted()

        verify(saveCustomerPort, times(1)).save(customer)
        assertEquals(customerResponse, customer)
    }

    private fun whenUseCaseIsExecuted(): Customer {
        return createCustomerUseCase.execute(customer)
    }

    private fun givenCustomer() = whenever(saveCustomerPort.save(customer)).thenReturn(customer)

    private val customer = Customer(
            "11121-2121",
            "John",
            "john@gmail.com",
            "45474"
    )
}