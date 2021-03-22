package authorizationservice.factories

import authorizationservice.application.dto.UserRequest

object UserRequestFactory {

    fun sample() = UserRequest(
        personId = "123456",
        name = "Test",
        email = "test@test.com",
        password = "@Test255",
        countryCode = PhoneFactory.sample().countryCode,
        areaCode = PhoneFactory.sample().areaCode,
        phoneNumber = PhoneFactory.sample().number
    )

}