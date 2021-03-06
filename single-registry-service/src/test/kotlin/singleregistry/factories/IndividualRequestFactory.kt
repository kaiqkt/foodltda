package singleregistry.factories

import singleregistry.application.dto.IndividualRequest

object IndividualRequestFactory {

    fun sample() = IndividualRequest(
        name = "Kaique Gomes",
        nickname = "kaique",
        cpf = "221.670.888-76",
        email = "test@test.com",
        street = "Rua ABC de D",
        number = "75",
        complement = "AP22",
        district = "Laoao",
        city = "Sao Campo",
        state = "Sao Paulo",
        country = "Brazil",
        postalCode = "09908781",
        countryCode = "335",
        areaCode = "11",
        phoneNumber = "40028922"
    )

}