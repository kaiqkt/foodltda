package com.foodltda.merchantservice.domain.entities

import org.springframework.data.annotation.Id

data class LoginUser(
        @Id
        val id: String? = null,
        val email: String,
        val password: String,
        val role: Profile = Profile.ROLE_USER
) {
    companion object {
        fun fromDocument(legalPerson: LegalPerson) =
                LoginUser(
                        id = legalPerson.id,
                        email = legalPerson.email,
                        password = legalPerson.password
                )
    }
}

enum class Profile {
    ROLE_ADMIN, ROLE_USER
}