package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.domain.entities.Address
import javax.validation.constraints.Email

data class UpdatePerson(
        val name: String? = null,
        @get:Email(message = "Invalid email.")
        val email: String? = null,
        val address: Address? = null,
        val password: String? = null,
        val telephone: String? = null
)