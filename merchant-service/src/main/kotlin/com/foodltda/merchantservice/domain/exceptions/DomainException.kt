package com.foodltda.merchantservice.domain.exceptions

abstract class DomainException : Exception {

    constructor() : super()
    constructor(message: String?) : super(message)

    open fun details(): ArrayList<String> = arrayListOf()
}