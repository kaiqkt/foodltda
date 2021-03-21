package authorizationservice.domain.entities

import java.util.concurrent.TimeUnit

data class AuthSessionDetail(
    val username: String?,
    val personId: String,
    val channel: String,
    val ip: String,
    val token: String,
    val expiration: Long,
    val timeUnit: TimeUnit = TimeUnit.HOURS
)