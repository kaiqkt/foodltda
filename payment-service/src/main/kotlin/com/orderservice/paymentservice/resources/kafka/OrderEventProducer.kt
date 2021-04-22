package com.orderservice.paymentservice.resources.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.orderservice.paymentservice.domain.events.Event
import com.orderservice.paymentservice.domain.events.OrderEvent
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.Header
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
): EventProducer {

    override fun send(event: Event, topic: String) {
        val producerRecord = getProducerRecord(event, topic)
        kafkaTemplate.send(producerRecord).get()
    }

    private fun getProducerRecord(event: Event, topic: String): ProducerRecord<String, String> {
        val serializedEvent = objectMapper.writeValueAsString(event)
        val eventTypeHeader = RecordHeader("eventType", objectMapper.writeValueAsBytes(
            OrderEvent::class.simpleName))
        val headers = listOf<Header>(eventTypeHeader)

        return ProducerRecord(
            topic,
            null,
            event.eventId,
            serializedEvent,
            headers)
    }
}