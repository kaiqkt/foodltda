package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.application.dto.Address

data class PersonRegistrationDTO (
    val name: String,
    val email: String,
    val address: Address,
    val cnpj: String,
    val password: String,
    val telephone: String,
)