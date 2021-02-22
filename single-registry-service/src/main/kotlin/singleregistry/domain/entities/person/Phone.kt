package singleregistry.domain.entities.person

data class Phone(
    val countryCode: String,
    val areaCode: String,
    val number: String
)