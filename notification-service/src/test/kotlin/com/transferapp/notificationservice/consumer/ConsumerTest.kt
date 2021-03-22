package com.transferapp.notificationservice.consumer

import com.nhaarman.mockitokotlin2.*
import com.transferapp.notificationservice.consumer.exception.NotificationServiceException
import com.transferapp.notificationservice.service.NotificationExtServiceProxy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.slf4j.Logger
import org.springframework.messaging.MessageHeaders

internal class ConsumerTest{

    private val logger: Logger = mock()
    private val notificationExtServiceProxy: NotificationExtServiceProxy = mock()
    private val consumer: Consumer = Consumer(logger, notificationExtServiceProxy)


    @Test
    fun `Test send sucessfull message`() {
        val message = givenMessage();
        val messageHeaders = givenMessageHeaders();
        consumer.receive(message, messageHeaders)
        verify(notificationExtServiceProxy, times(1)).sendNotification(message)
    }

    @Test
    fun `Test send error message`() {
        val message = givenMessage();
        val messageHeaders = givenMessageHeaders();
        givenNotificationExtServiceProxy(message)

        Assertions.assertThrows(NotificationServiceException::class.java) {
            consumer.receive(message, messageHeaders)
        }
        verify(notificationExtServiceProxy, times(1)).sendNotification(message)
    }

    private fun givenNotificationExtServiceProxy(message:String){
        whenever(notificationExtServiceProxy.sendNotification(message)).thenThrow(RuntimeException())
    }
    private fun givenMessage() = "Transfer to acoount 123. value 2213.112"
    private fun givenMessageHeaders() = MessageHeaders(mapOf("MessageId" to "231332"))


}