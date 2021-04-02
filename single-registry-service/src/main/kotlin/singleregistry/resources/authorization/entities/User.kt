package singleregistry.resources.authorization.entities

data class User(
    val personId: String?,
    val name: String?,
    val email: String?,
    val password: String?,
    val countryCode: String?,
    val areaCode: String?,
    val phoneNumber: String?
)
