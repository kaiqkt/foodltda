package authorizationservice.factories

import authorizationservice.domain.entities.User

object UserFactory {

    fun sample() = User(
        _id = "7568",
        personId = "123456",
        email = "test@test.com",
        password = "@Test255",
        phone = PhoneFactory.sample(),
    )
}