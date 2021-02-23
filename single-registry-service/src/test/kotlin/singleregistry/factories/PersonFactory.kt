package singleregistry.factories

import singleregistry.domain.entities.person.Address
import singleregistry.domain.entities.person.Person
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.entities.person.Phone
import java.time.LocalDateTime

object PersonFactory {

    fun sample(
        personId: String = "6033c9578af83d0beaf2159f",
        email: String = "test@test.com",
        address: Address = AddressFactory.sample(),
        phone: Phone = PhoneFactory.sample(),
        personType: PersonType
    ) = Person(
        personId = personId,
        email = email,
        address = address,
        phone = phone,
        personType = personType,
        createdAt = LocalDateTime.now()
    )
}