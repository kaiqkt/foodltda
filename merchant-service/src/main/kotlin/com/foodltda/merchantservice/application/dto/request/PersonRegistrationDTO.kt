package com.foodltda.merchantservice.application.dto.request

import com.foodltda.merchantservice.application.dto.Address
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PersonRegistrationDTO(
        @get:NotEmpty(message = "Name cannot be empty.")
        val name: String = "",
        @get:NotEmpty(message = "Email cannot be empty.")
        @get:Email(message = "Invalid email.")
        val email: String = "",
        @get:NotNull(message = "Address cannot be empty.")
        val address: Address?,
        @get:NotEmpty(message = "Cnpj cannot be empty.")
        val cnpj: String = "",
        @get:NotEmpty(message = "Password cannot be empty.")
        val password: String = "",
        @get:NotEmpty(message = "Telephone cannot be empty.")
        val telephone: String = ""
)