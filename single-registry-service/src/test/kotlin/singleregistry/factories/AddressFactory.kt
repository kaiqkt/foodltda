package singleregistry.factories

import singleregistry.domain.entities.person.Address

object AddressFactory {

    fun sample() = Address(
        street = "Rua ABC de D",
        number = "75",
        complement = "AP22",
        district = "Laoao",
        city = "Sao Campo",
        state = "Sao Paulo",
        country = "Brazil",
        postalCode = "09908781"
    )
}