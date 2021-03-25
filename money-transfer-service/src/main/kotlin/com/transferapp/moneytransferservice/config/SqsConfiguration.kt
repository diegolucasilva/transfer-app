package com.transferapp.moneytransferservice.config


import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SqsConfiguration {

    @Value("\${sqs.region}")
    private val REGION: String? = null
    @Value("\${sqs.endpoint}")
    private val SQS_ENPOINT: String? = null
    @Value("\${sqs.queueName}")
    private val SQS_QUEUE_NAME: String? = null

    @Bean
    fun amazonSQS(): AmazonSQS{
        return AmazonSQSClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(SQS_ENPOINT,REGION))
            .build()
    }

}