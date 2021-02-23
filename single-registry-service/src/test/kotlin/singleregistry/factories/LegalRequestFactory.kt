package singleregistry.factories

import singleregistry.application.dto.LegalRequest
import singleregistry.domain.entities.legal.BusinessType
import singleregistry.domain.entities.person.PersonType
import java.time.LocalDateTime

object LegalRequestFactory {

    fun sample() = LegalRequest(
        businessName = "Restaurant",
        cnpj = "10.501.210/0001-17",
        businessType = BusinessType.LTDA,
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