package singleregistry.domain.entities.person

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import singleregistry.domain.entities.Address
import singleregistry.domain.entities.PersonType
import singleregistry.domain.entities.Phone
import java.time.LocalDateTime

@Document
data class Person(
    @Id
    val personId: String? = null,
    val email: String?,
    val address: Address?,
    val phone: Phone?,
    val personType: PersonType? = null,
    val creationDate: LocalDateTime? = null
)