package com.orderservice.paymentservice.resources.config

import com.orderservice.paymentservice.domain.constants.PAYMENT_FAILURE_TOPIC
import com.orderservice.paymentservice.domain.constants.PAYMENT_REVERT_TOPIC
import com.orderservice.paymentservice.domain.constants.PAYMENT_SUCCESS_TOPIC
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class AutoCreateConfig {
    @Bean
    fun createSuccessTopic(): NewTopic = TopicBuilder.name(
        PAYMENT_SUCCESS_TOPIC).partitions(3).replicas(1).build()
    @Bean
    fun createFailureTopic(): NewTopic = TopicBuilder.name(
        PAYMENT_FAILURE_TOPIC).partitions(3).replicas(1).build()
    @Bean
    fun createRevertTopic(): NewTopic = TopicBuilder.name(
        PAYMENT_REVERT_TOPIC).partitions(3).replicas(1).build()
}