package com.transferapp.notificationservice.consumer

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.transferapp.notificationservice.service.NotificationExtServiceProxy
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.slf4j.Logger
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer

internal class NotificationHealthCheckerTest{


    private val logger: Logger = mock()
    private val simpleMessageListenerContainer: SimpleMessageListenerContainer = mock()
    private val notificationExtServiceProxy: NotificationExtServiceProxy =  mock()
    private val notificationHealthChecker = NotificationHealthChecker(logger, simpleMessageListenerContainer, notificationExtServiceProxy )

    @Test
    fun `Test notification service healthy`() {
        notificationHealthChecker.verifyNotificationServiceHealth()

        verify(notificationExtServiceProxy, times(1)).healthcheck()
        verify(simpleMessageListenerContainer, times(1)).isRunning(null)

    }

    @Test
    fun `Test notification service becomes healthy should start polling`() {
        givenListenerContainerStopped()

        notificationHealthChecker.verifyNotificationServiceHealth()

        verify(notificationExtServiceProxy, times(1)).healthcheck()
        verify(simpleMessageListenerContainer, times(1)).isRunning(null)
        verify(simpleMessageListenerContainer, times(1)).start(null)

    }

    @Test
    fun `Test notification service unhealthy should stop polling `() {
        givenNotificationExtServiceProxyUnhealthy()

        notificationHealthChecker.verifyNotificationServiceHealth()

        verify(notificationExtServiceProxy, times(1)).healthcheck()
        verify(simpleMessageListenerContainer, times(1)).isRunning(null)
        verify(simpleMessageListenerContainer, times(1)).stop(null)

    }

    private fun givenListenerContainerStopped(){
        whenever(simpleMessageListenerContainer.isRunning(null)).thenReturn(false)
    }


    private fun givenNotificationExtServiceProxyUnhealthy(){
        whenever(notificationExtServiceProxy.healthcheck()).thenThrow(RuntimeException())
        whenever(simpleMessageListenerContainer.isRunning(null)).thenReturn(true)
    }


}