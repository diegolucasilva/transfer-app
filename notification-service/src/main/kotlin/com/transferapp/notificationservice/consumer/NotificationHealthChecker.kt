package com.transferapp.notificationservice.consumer

import com.transferapp.notificationservice.service.NotificationExtServiceProxy
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import javax.annotation.PostConstruct

@Configuration
@EnableScheduling
class NotificationHealthChecker(
    private val logger: Logger,
    private val simpleMessageListenerContainer: SimpleMessageListenerContainer,
    private val notificationExtServiceProxy: NotificationExtServiceProxy
){
    @Value("\${sqs.queueName}")
    private val SQS_QUEUE_NAME: String? = null
    private val RECEIVE_MESSAGE_QUEUE_TIME_OUT_MILLISECONDS = 20000L


    @Scheduled(fixedDelay =30000)
    fun verifyNotificationServiceHealth(){
            try {
                notificationExtServiceProxy.healthcheck()
            } catch (e: Exception) {
                stopPollingQueue()
            }
            if (!simpleMessageListenerContainer.isRunning(SQS_QUEUE_NAME)) {
                logger.info("Starting queue listener")
                simpleMessageListenerContainer.start(SQS_QUEUE_NAME)
        }
    }

    private fun stopPollingQueue(){
        logger.info("service unhealthy")
        if (simpleMessageListenerContainer.isRunning(SQS_QUEUE_NAME)) {
            simpleMessageListenerContainer.stop(SQS_QUEUE_NAME)
            logger.info("Stopping queue listener")
        } else
            logger.info("Queue listener already stopped")
    }

    @PostConstruct
    private fun init(){
        simpleMessageListenerContainer.
        setQueueStopTimeout(RECEIVE_MESSAGE_QUEUE_TIME_OUT_MILLISECONDS)
    }

}