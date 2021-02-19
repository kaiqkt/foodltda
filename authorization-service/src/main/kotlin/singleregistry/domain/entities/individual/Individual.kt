package singleregistry.domain.entities.individual

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import singleregistry.domain.entities.person.Address
import singleregistry.domain.entities.person.Person
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.entities.person.Phone
import java.time.LocalDateTime

@Document
data class Individual(
    @Id
    val userId: String? = null,
    val name: String?,
    val nickname: String?,
    val email: String?,

    override val personId: String?,
    override val address: Address?,
    override val phone: Phone,
    override val creationDate: LocalDateTime? = null
) : Person(
    personId,
    address,
    phone,
    PersonType.PF,
    creationDate
)