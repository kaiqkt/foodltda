package com.zed.restaurantservice.resources.singleregistry.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Person(
    @Id
    val personId: String? = null,
    val email: String?,
    val address: Address?,
    val phone: Phone?,
    val type: PersonType? = null,
    val createdAt: LocalDateTime? = null
)