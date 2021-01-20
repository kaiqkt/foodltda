package com.foodltda.merchantservice.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.foodltda.merchantservice.application.dto.Address
import com.foodltda.merchantservice.application.dto.request.PersonRegistrationDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class LegalPerson (
    @Id
    val id: String? = null,
    val name: String?,
    val email: String?,
    val cnpj: String?,
    val address: Address?,
    @JsonIgnore
    val password: String?,
    val telephone: String?,
) {
    companion object {
        fun fromDocument(person: PersonRegistrationDTO) =
                LegalPerson (
                        name = person.name,
                        email = person.email,
                        address = person.address,
                        cnpj = person.cnpj,
                        password = person.password,
                        telephone = person.telephone
                )
    }
}