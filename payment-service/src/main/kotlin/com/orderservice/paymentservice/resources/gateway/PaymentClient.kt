package com.orderservice.paymentservice.resources.gateway

interface PaymentClient {
    fun result(): Boolean
}