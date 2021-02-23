package singleregistry.factories

import singleregistry.domain.entities.person.Phone

object PhoneFactory {

    fun sample() = Phone(
        countryCode = "335",
        areaCode = "11",
        number =  "40028922"
    )
}