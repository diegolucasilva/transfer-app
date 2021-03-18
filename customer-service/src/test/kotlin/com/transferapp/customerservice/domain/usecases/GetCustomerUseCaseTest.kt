package com.transferapp.customerservice.domain.usecases


import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.transferapp.customerservice.adapter.out.persistence.CustomerNotFoundException
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerPort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class GetCustomerUseCaseTest{

    private val getCustomerPort: GetCustomerPort = mock()
    private val getCustomerUseCase = GetCustomerUseCase(getCustomerPort)

    @Test
    fun `Test Get Customer Use Case - verify if customer is returned`() {
        val id= "6666"
        givenCustomer(id)

        val customerResponse = whenUseCaseIsExecuted(id)

        verify(getCustomerPort, times(1)).getById(id)
        Assertions.assertEquals(customerResponse, customer)
    }

    @Test
    fun `Test Get Customer Use Case - verify if throws customer not found exeception`() {
        val wrongId= ""
        givenCustomer(wrongId)

        assertThrows(CustomerNotFoundException::class.java) {
            whenUseCaseIsExecuted(wrongId)
        }
        verify(getCustomerPort, times(1)).getById(wrongId)

    }

    private fun whenUseCaseIsExecuted(id:String) = getCustomerUseCase.execute(id)

    private fun givenCustomer(id:String){
        if(id.isNotBlank())
            whenever(getCustomerPort.getById(id)).thenReturn(customer)
        else
        whenever(getCustomerPort.getById(id)).thenThrow(CustomerNotFoundException::class.java)
    }

    private val customer = Customer(
            "6666",
            "John",
            "john@gmail.com",
            "45474"
    )
}