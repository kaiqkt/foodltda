package singleregistry.domain.entities.person

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
abstract class Person(
    @Id
    val personId: String? = null,
    val address: Address?,
    val phone: Phone,
    val type: PersonType,
    val creationDate: LocalDateTime? = null
)
