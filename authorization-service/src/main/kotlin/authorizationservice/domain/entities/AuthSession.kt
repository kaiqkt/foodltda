package authorizationservice.domain.entities

import java.io.Serializable
import java.util.concurrent.TimeUnit

data class AuthSession(
    val userId: String?,
    val username: String?,
    val personId: String?,
    val channel: Channel,
    val ip: String,
    val token: String,
    val expiration: Long,
    val timeUnit: TimeUnit
): Serializable
