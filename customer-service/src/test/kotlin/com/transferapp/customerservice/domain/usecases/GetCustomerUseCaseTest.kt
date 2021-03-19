package com.transferapp.customerservice.domain.usecases


import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.transferapp.customerservice.adapter.out.persistence.entity.CustomerEntity
import com.transferapp.customerservice.adapter.out.persistence.exception.CustomerNotFoundException
import com.transferapp.customerservice.adapter.utils.toDomain
import com.transferapp.customerservice.domain.entity.RoleType
import com.transferapp.customerservice.domain.port.out.persistence.GetCustomerByDocumentIdPort
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

internal class GetCustomerUseCaseTest{

    private val getCustomerByDocumentIdPort: GetCustomerByDocumentIdPort = mock()
    private val getCustomerUseCase = GetCustomerUseCase(getCustomerByDocumentIdPort)

    @Test
    fun `Test Get Customer Use Case - verify if customer is returned`() {
        val id= "45474"
        givenCustomer(id)

        val customerResponse = whenUseCaseIsExecuted(id)

        verify(getCustomerByDocumentIdPort, times(1)).getByDocumentId(id)
        Assertions.assertEquals(customerResponse, customerEntity.toDomain())
    }

    @Test
    fun `Test Get Customer Use Case - verify if throws customer not found exeception`() {
        val wrongId= ""
        givenCustomer(wrongId)

        assertThrows(CustomerNotFoundException::class.java) {
            whenUseCaseIsExecuted(wrongId)
        }
        verify(getCustomerByDocumentIdPort, times(1)).getByDocumentId(wrongId)

    }

    private fun whenUseCaseIsExecuted(id:String) = getCustomerUseCase.execute(id)

    private fun givenCustomer(id:String){
        if(id.isNotBlank())
            whenever(getCustomerByDocumentIdPort.getByDocumentId(id)).thenReturn(Optional.of(customerEntity))
        else
        whenever(getCustomerByDocumentIdPort.getByDocumentId(id)).thenThrow(CustomerNotFoundException::class.java)
    }

    private val customerEntity = CustomerEntity(
            "6666",
            "John",
            "john@gmail.com",
            "45474",
            1,
            RoleType.USER
    )
}