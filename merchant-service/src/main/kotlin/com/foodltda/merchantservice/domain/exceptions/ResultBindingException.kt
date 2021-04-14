package com.foodltda.merchantservice.domain.exceptions

import com.foodltda.merchantservice.application.dto.response.Response


class ResultBindingException(private val response: Response<Any>): DomainException() {

    override fun details() = response.errors
}
