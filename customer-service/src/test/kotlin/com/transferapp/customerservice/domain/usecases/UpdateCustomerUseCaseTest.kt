package com.transferapp.customerservice.domain.usecases


import com.nhaarman.mockitokotlin2.*
import com.transferapp.customerservice.adapter.out.persistence.CustomerNotFoundException
import com.transferapp.customerservice.domain.entity.Customer
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerPort
import com.transferapp.customerservice.domain.port.out.persistence.SaveCustomerPort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UpdateCustomerUseCaseTest{
    private val getCustomerPort: GetCustomerPort = mock()
    private val saveCustomerPort: SaveCustomerPort = mock()
    private val updateCustomerUseCase = UpdateCustomerUseCase(saveCustomerPort, getCustomerPort)

    @Test
    fun `Test Update Customer Use Case - verify if customer is returned`() {
        val id= "6666"
        givenCustomerDTORequest(id)

        whenUseCaseIsExecuted(id)

        verify(getCustomerPort, times(1)).getById(id)
        verify(saveCustomerPort, times(1)).save(any<Customer>())
    }

    @Test
    fun `Test Get Customer Use Case - verify if throws customer not found exeception`() {
        val wrongId= ""
        givenCustomerDTORequest(wrongId)

        Assertions.assertThrows(CustomerNotFoundException::class.java) {
            whenUseCaseIsExecuted(wrongId)
        }
        verify(getCustomerPort, times(1)).getById(wrongId)

    }

    private fun whenUseCaseIsExecuted(id:String) = updateCustomerUseCase.execute(id,customer)


    private fun givenCustomerDTORequest(id:String){
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