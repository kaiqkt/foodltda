package authorizationservice.domain.entities

data class Session(
    val _id: String? = null,
    val username: String,
    val personId: String,
    val channel: String,
    val ip: String,
    val token: String,
    val expiration: String? = null
)