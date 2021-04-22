package com.orderservice.orderservice.resources.kafka.config

import com.orderservice.orderservice.domain.constants.START_TOPIC
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class AutoCreateConfig {

    @Bean
    fun libraryEvents(): NewTopic {
        return TopicBuilder.name(START_TOPIC)
            .partitions(3)
            .replicas(1)
            .build()
    }
}