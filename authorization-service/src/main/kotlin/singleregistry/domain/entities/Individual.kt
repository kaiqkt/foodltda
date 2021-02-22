package singleregistry.domain.entities

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import singleregistry.domain.entities.person.Person
import java.time.LocalDateTime

data class Individual(
    @Id
    val id: String? = null,
    val name: String?,
    val nickname: String?,
    val cpf: String?,
    val person: Person
)