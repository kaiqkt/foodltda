package authorizationservice.domain.entities

data class Phone(
    val countryCode: String,
    val areaCode: String,
    val number: String
)