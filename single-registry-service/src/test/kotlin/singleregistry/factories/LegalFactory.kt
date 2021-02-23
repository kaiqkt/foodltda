package singleregistry.factories

import singleregistry.application.dto.toDomain
import singleregistry.application.dto.toPerson
import singleregistry.domain.entities.legal.BusinessType
import singleregistry.domain.entities.legal.Legal
import singleregistry.domain.entities.person.PersonType

object LegalFactory {

    fun sample() = Legal(
        businessName = "Restaurant",
        cnpj = "10.501.210/0001-17",
        businessType = BusinessType.LTDA,
        person = PersonFactory.sample(personType = PersonType.PJ)
    )
}