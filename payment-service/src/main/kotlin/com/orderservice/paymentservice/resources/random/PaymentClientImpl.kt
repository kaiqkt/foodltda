package com.orderservice.paymentservice.resources.random

import com.orderservice.paymentservice.resources.gateway.PaymentClient
import org.springframework.stereotype.Component
import java.util.*

//verificar se o restaurant tem os meios de pagamento
@Component
class PaymentClientImpl : PaymentClient {

    override fun result(): Boolean {
        return generateResult(rand())
    }

    private fun generateResult(number: Int): Boolean {
        require(number in 0..4)
        return number != 4
    }

    private fun rand(start: Int = 0, end: Int = 4): Int {
        require(!(start > end || end - start + 1 > Int.MAX_VALUE)) { "Illegal Argument" }
        return Random(System.nanoTime()).nextInt(end - start + 1) + start
    }
}