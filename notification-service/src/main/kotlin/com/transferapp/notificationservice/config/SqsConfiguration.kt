package com.transferapp.notificationservice.config


import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@EnableSqs
class SqsConfiguration {

    @Value("\${sqs.region}")
    private val REGION: String? = null
    @Value("\${sqs.endpoint}")
    private val SQS_ENPOINT: String? = null
    @Value("\${sqs.queueName}")
    private val SQS_QUEUE_NAME: String? = null

    @Bean
    @Primary
    fun amazonSQSAsync(): AmazonSQSAsync{
        return AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(SQS_ENPOINT,REGION))
            .build()
    }

    @Bean
    fun queueMessagingTemplate(): QueueMessagingTemplate{
        return QueueMessagingTemplate(amazonSQSAsync())
    }

    @Bean
    fun simpleMessageListenerContainerFactory(amazonSQSAsync: AmazonSQSAsync): SimpleMessageListenerContainerFactory{
        val factory = SimpleMessageListenerContainerFactory()
        factory.setAmazonSqs(amazonSQSAsync)
        factory.setAutoStartup(true)
        factory.setMaxNumberOfMessages(10)
        factory.setWaitTimeOut(20)
        return factory
    }
}