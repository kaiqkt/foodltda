package authorizationservice.application.dto

import authorizationservice.domain.entities.Phone
import authorizationservice.domain.entities.User
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class UserRequest(
    @get:NotEmpty(message = "PersonId cannot be empty.")
    val personId: String = "",
    @get:NotEmpty(message = "Name cannot be empty.")
    val name: String = "",
    @get:NotEmpty(message = "Email cannot be empty.")
    val email: String = "",
    @get:NotEmpty(message = "Password cannot be empty.")
//    @get:Pattern(regexp = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#\$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}\$", message = "Wrong password required")
    val password: String = "",
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

fun UserRequest.toDomain() = User(
    personId = this.personId,
    name = this.name,
    email = this.email,
    password = this.password,
    phone = this.toPhone()
)

fun UserRequest.toPhone() = Phone(
    countryCode = this.countryCode,
    areaCode = this.areaCode,
    number = this.phoneNumber
)

//^                         Start anchor
//(?=.*[A-Z].*[A-Z])        Ensure string has two uppercase letters.
//(?=.*[!@#$&*])            Ensure string has one special case letter.
//(?=.*[0-9].*[0-9])        Ensure string has two digits.
//(?=.*[a-z].*[a-z].*[a-z]) Ensure string has three lowercase letters.
//.{8}                      Ensure string is of length 8.
//$                         End anchor.