package authorizationservice.factories

import authorizationservice.domain.entities.Phone

object PhoneFactory {

    fun sample() = Phone(
        countryCode = "335",
        areaCode = "11",
        number =  "40028922"
    )
}