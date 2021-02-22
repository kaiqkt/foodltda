package singleregistry.application.dto

import singleregistry.domain.entities.Address
import singleregistry.domain.entities.PersonType
import singleregistry.domain.entities.Phone
import singleregistry.domain.entities.person.Person
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class PersonRequest(
    @get:NotEmpty(message = "Email cannot be empty.")
    @get:Email(message = "Invalid email.")
    val email: String? = null,
    @get:NotNull(message = "Address cannot be null.")
    val address: AddressRequest? = null,
    @get:NotNull(message = "Phone cannot be null.")
    val phone: PhoneRequest? = null,
)

data class AddressRequest(
    @get:NotEmpty(message = "Street cannot be empty.")
    val street: String? = null,
    @get:NotEmpty(message = "Number cannot be empty.")
    val number: String? = null,
    @get:NotEmpty(message = "Complement cannot be empty.")
    val complement: String? = null,
    @get:NotEmpty(message = "District cannot be empty.")
    val district: String,
    @get:NotEmpty(message = "City cannot be empty.")
    val city: String? = null,
    @get:NotEmpty(message = "State cannot be empty.")
    val state: String? = null,
    @get:NotEmpty(message = "Country cannot be empty.")
    val country: String? = null,
    @get:NotEmpty(message = "PostalCode cannot be empty.")
    val postalCode: String? = null
)

data class PhoneRequest(
    @Pattern(regexp = "\\+?[0-9]{2}", message = "Country code invalid")
    val countryCode: String,
    @Pattern(regexp = "[0-9]{2}", message = "Area code invalid")
    val areaCode: String,
    @Pattern(regexp = "[0-9]{9}", message = "Number invalid")
    val number: String
)

fun PersonRequest.toDomain() = Person(
    email = this.email,
    address = this.address?.toDomain(),
    phone = this.phone?.toDomain(),
    creationDate = LocalDateTime.now()
)

fun AddressRequest.toDomain() = Address(
    street = this.street,
    number = this.number,
    complement = this.complement,
    district = this.district,
    city = this.city,
    state = this.state,
    country = this.country,
    postalCode = this.postalCode
)

fun PhoneRequest.toDomain() = Phone(
    countryCode = this.countryCode,
    areaCode = this.areaCode,
    number = this.number
)
