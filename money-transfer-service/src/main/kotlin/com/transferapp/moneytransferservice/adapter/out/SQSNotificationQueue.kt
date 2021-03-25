package com.transferapp.moneytransferservice.adapter.out

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.transferapp.moneytransferservice.domain.port.out.dto.SendNotificationPort
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SQSNotificationQueue(private val amazonSQS: AmazonSQS,
                           private val logger: Logger
): SendNotificationPort {

    @Value("\${sqs.queueName}")
    private val SQS_QUEUE_NAME: String? = null
    @Value("\${sqs.endpoint}")
    private val SQS_ENPOINT: String? = null

    override fun send(message: String) {
        try {
            val sendMessageRequest = messageRequestBuilder(message)
            amazonSQS.sendMessage(sendMessageRequest)
            logger.info("message sent  [SQS QUEUE = {} Payload = {}]]", SQS_QUEUE_NAME, message)
        }catch(ex: Exception){
            logger.error("can´t send message [SQS QUEUE = {} Payload = {}]]", SQS_QUEUE_NAME, message)
            logger.error(ex.toString())
        }
    }

    fun messageRequestBuilder(message: String): SendMessageRequest{
        val queueUrl = SQS_ENPOINT+SQS_QUEUE_NAME
        return SendMessageRequest(queueUrl, message)
    }

}