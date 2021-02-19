package singleregistry.domain.entities.person

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
abstract class Person(
    @Id
    open val personId: String? = null,
    open val address: Address?,
    open val phone: Phone,
    val type: PersonType,
    open val creationDate: LocalDateTime? = null
)
