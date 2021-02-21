package singleregistry.domain.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "legal")
@TypeAlias("legal")
data class Legal(
    @Id
    val personId: String? = null,
    val businessName: String?,
    @Indexed(unique = true, sparse = true)
    val cnpj: String?,
    val businessType: BusinessType,
    @Indexed(unique = true)
    val email: String?,
    val address: Address?,
    val phone: Phone,
    val personType: PersonType = PersonType.PJ,
    val creationDate: LocalDateTime? = null
)
