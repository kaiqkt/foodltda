package authorizationservice.factories

import authorizationservice.domain.entities.AuthSessionDetail
import java.util.UUID
import java.util.concurrent.TimeUnit

object AuthSessionDetailFactory {

    fun sample() = AuthSessionDetail(
        username = "Test@test.com",
        personId = "12345",
        channel = "APP_MOBILE",
        ip = "10.0.0.7",
        token = UUID.randomUUID().toString(),
        expiration = 2,
        timeUnit = TimeUnit.HOURS
    )
}