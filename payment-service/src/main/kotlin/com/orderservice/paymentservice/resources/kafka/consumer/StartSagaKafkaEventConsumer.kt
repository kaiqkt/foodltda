package com.orderservice.paymentservice.resources.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.orderservice.paymentservice.domain.constants.START_TOPIC
import com.orderservice.paymentservice.domain.events.OrderEvent
import com.orderservice.paymentservice.domain.services.PaymentService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class StartSagaKafkaEventConsumer(
    @Autowired val service: PaymentService,
    @Autowired val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = [START_TOPIC])
    fun onMessage(consumerRecord: ConsumerRecord<String, String>) {
        val startSagaEvent = objectMapper.readValue(
            consumerRecord.value(),
            OrderEvent::class.java
        )
        val order = startSagaEvent.order
        logger.info(
            "event consumed ${
                String(
                    consumerRecord.headers().first().value()
                )
            } id ${consumerRecord.key()} from topic  ${consumerRecord.topic()} for person ${order.personId}"
        )
        service.execute(order)
    }
}
