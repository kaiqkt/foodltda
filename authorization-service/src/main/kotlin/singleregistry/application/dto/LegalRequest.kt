package singleregistry.application.dto

import org.hibernate.validator.constraints.br.CNPJ
import singleregistry.domain.entities.*
import singleregistry.domain.entities.person.Person
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class LegalRequest(
    @get:NotEmpty(message = "BusinessName cannot be empty.")
    val businessName: String = "",
    @get:NotEmpty(message = "CNPJ cannot be empty.")
    @get:CNPJ(message = "Invalid cnpj.")
    val cnpj: String = "",
    @get:NotNull(message = "Business Type cannot be null.")
    val businessType: BusinessType? = null,
    @get:NotEmpty(message = "Email cannot be empty.")
    @get:Email(message = "Invalid email.")
    val email: String? = null,
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
    val postalCode: String? = null,
    @get:NotEmpty(message = "PostalCode cannot be empty.")
    @Pattern(regexp = "\\+?[0-9]{2}", message = "Country code invalid")
    val countryCode: String,
    @get:NotEmpty(message = "PostalCode cannot be empty.")
    @Pattern(regexp = "[0-9]{2}", message = "Area code invalid")
    val areaCode: String,
    @get:NotEmpty(message = "PostalCode cannot be empty.")
    @Pattern(regexp = "[0-9]{9}", message = "Number invalid")
    val phoneNumber: String
)

fun LegalRequest.toDomain() =
    Legal(
        businessName = this.businessName,
        cnpj = this.cnpj,
        businessType = this.businessType,
        person = this.toPerson()
    )

fun LegalRequest.toPerson() = Person(
    email = this.email,
    address = this.toAddress(),
    phone = this.toPhone(),
    personType = PersonType.PJ,
    creationDate = LocalDateTime.now()
)

fun LegalRequest.toAddress() = Address(
    street = this.street,
    number = this.number,
    complement = this.complement,
    district = this.district,
    city = this.city,
    state = this.state,
    country = this.country,
    postalCode = this.postalCode
)

fun LegalRequest.toPhone() = Phone(
    countryCode = this.countryCode,
    areaCode = this.areaCode,
    number = this.phoneNumber
)

