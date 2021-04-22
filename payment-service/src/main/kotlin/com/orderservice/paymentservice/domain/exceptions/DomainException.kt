package com.orderservice.paymentservice.domain.exceptions

abstract class DomainException : Exception {

    constructor() : super()
    constructor(message: String) : super(message)
    open fun details(): List<String> = arrayListOf()
}
