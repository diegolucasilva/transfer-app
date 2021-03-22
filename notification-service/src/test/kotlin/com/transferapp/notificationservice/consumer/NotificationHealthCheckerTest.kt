package com.transferapp.notificationservice.consumer

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.transferapp.notificationservice.service.NotificationExtServiceProxy
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer

internal class NotificationHealthCheckerTest{


    private val logger: Logger = mock()
    private val simpleMessageListenerContainer: SimpleMessageListenerContainer = mock()
    private val notificationExtServiceProxy: NotificationExtServiceProxy =  mock()
    private val notificationHealthChecker = NotificationHealthChecker(logger, simpleMessageListenerContainer, notificationExtServiceProxy )

    @Test
    fun `Test notification service health`() {
        notificationHealthChecker.verifyNotificationServiceHealth()
        verify(notificationExtServiceProxy, times(1)).healthcheck()
    }



}