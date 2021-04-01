package authorizationservice.factories

import authorizationservice.domain.entities.AuthSession
import authorizationservice.domain.entities.Channel
import java.util.UUID
import java.util.concurrent.TimeUnit

object AuthSessionFactory {

    fun sample() = AuthSession(
        userId = "23142",
        username = "Test@test.com",
        personId = "12345",
        channel = Channel.APP_MOBILE,
        ip = "10.0.0.7",
        token = UUID.randomUUID().toString(),
        expiration = 2,
        timeUnit = TimeUnit.SECONDS
    )
}