package singleregistry.application.dto

import org.hibernate.validator.constraints.br.CPF
import singleregistry.domain.entities.individual.Individual
import singleregistry.domain.entities.person.Address
import singleregistry.domain.entities.person.Person
import singleregistry.domain.entities.person.PersonType
import singleregistry.domain.entities.person.Phone
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class IndividualRequest(
    @get:NotEmpty(message = "Name cannot be empty.")
    val name: String = "",
    @get:NotNull(message = "Nickname Type cannot be null.")
    val nickname: String = "",
    @get:NotEmpty(message = "CPF cannot be empty.")
    @get:CPF(message = "Invalid CPF.")
    val cpf: String = "",
    @get:NotEmpty(message = "Email cannot be empty.")
    @get:Email(message = "Invalid email.")
    val email: String? = null,
    @get:Pattern(regexp = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#\$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}\$", message = "Wrong password required")
    val password: String = "",
    @get:NotEmpty(message = "Street cannot be empty.")
    val street: String? = null,
    @get:NotEmpty(message = "Number cannot be empty.")
    val number: String? = null,
    val complement: String? = null,
    @get:NotEmpty(message = "District cannot be empty.")
    val district: String = "",
    @get:NotEmpty(message = "City cannot be empty.")
    val city: String? = null,
    @get:NotEmpty(message = "State cannot be empty.")
    val state: String? = null,
    @get:NotEmpty(message = "Country cannot be empty.")
    val country: String? = null,
    @get:NotEmpty(message = "PostalCode cannot be empty.")
    @get:Pattern(regexp = "[0-9]{7}", message = "Postal code invalid")
    val postalCode: String? = null,
    @get:NotEmpty(message = "Country Code cannot be empty.")
    @get:Pattern(regexp = "\\+?[0-9]{2}", message = "Country code invalid")
    val countryCode: String = "",
    @get:NotEmpty(message = "Area Code cannot be empty.")
    @get:Pattern(regexp = "[0-9]{2}", message = "Area code invalid")
    val areaCode: String = "",
    @get:NotEmpty(message = "Phone Number cannot be empty.")
    @get:Pattern(regexp = "[0-9]{9}", message = "Number invalid")
    val phoneNumber: String = ""
)

fun IndividualRequest.toDomain() = Individual(
    name = this.name,
    nickname = this.nickname,
    cpf = this.cpf,
    person = this.toPerson()
)

fun IndividualRequest.toPerson() = Person(
    email = this.email,
    address = this.toAddress(),
    phone = this.toPhone(),
    type = PersonType.PF,
    createdAt = LocalDateTime.now()
)

fun IndividualRequest.toAddress() = Address(
    street = this.street,
    number = this.number,
    complement = this.complement,
    district = this.district,
    city = this.city,
    state = this.state,
    country = this.country,
    postalCode = this.postalCode
)

fun IndividualRequest.toPhone() = Phone(
    countryCode = this.countryCode,
    areaCode = this.areaCode,
    number = this.phoneNumber
)