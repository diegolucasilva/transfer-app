package com.transferapp.notificationservice.consumer

import com.transferapp.notificationservice.consumer.exception.NotificationServiceException
import com.transferapp.notificationservice.service.NotificationExtServiceProxy
import org.slf4j.Logger
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val logger: Logger,
    private val notificationExtServiceProxy: NotificationExtServiceProxy
) {

    @SqsListener(value = ["\${sqs.queueName}"], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun receive(@Payload message:String, @Headers messageHeaders: MessageHeaders){
       val messageId = messageHeaders.get("MessageId")
        logger.info("Received message [ID = {}, Payload = {}]]", messageId, message)
        try {
            notificationExtServiceProxy.sendNotification(message)
        }catch(e: Exception){
            logger.error("Error processing message [ID = {}, Payload = {}]]", messageId, message)
            throw NotificationServiceException(e)
        }
        logger.info("message processed [ID = {}]",messageId)
    }
}