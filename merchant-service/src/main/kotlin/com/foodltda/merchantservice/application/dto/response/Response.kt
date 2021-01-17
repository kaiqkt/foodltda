package com.foodltda.merchantservice.application.dto.response

data class Response<T> (
        var data: T? = null,
        var errors: ArrayList<String> = arrayListOf()
)