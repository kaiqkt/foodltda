package singleregistry.domain.entities.legal

import org.springframework.data.annotation.Id
import singleregistry.domain.entities.legal.BusinessType
import singleregistry.domain.entities.person.Person

data class Legal(
    @Id
    val _id: String? = null,
    val businessName: String?,
    val cnpj: String?,
    val businessType: BusinessType?,
    val person: Person
)
