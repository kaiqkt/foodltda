package authorizationservice.application.dto

import authorizationservice.domain.entities.Phone
import authorizationservice.domain.entities.User
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class UserRequest(
    @get:NotEmpty(message = "PersonId cannot be empty.")
    val personId: String = "",
    @get:NotEmpty(message = "Name cannot be empty.")
    val name: String = "",
    @get:NotEmpty(message = "Email cannot be empty.")
    val email: String = "",
    @get:NotEmpty(message = "Password cannot be empty.")
    //Mínimo de oito caracteres, pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial:
    @Pattern(regexp = "\"(?=.*[az])(?=.*[AZ])(?=.*\\d)(?=.*[\$@!%*?&])[A-Za-z\\d\$@!%*?&]{8,}\"", message = "Strong password required")
    val password: String = "",
    @get:NotNull(message = "PostalCode cannot be empty.")
    val phone: Phone? = null
)

fun UserRequest.toDomain() = User(
    personId = this.personId,
    name = this.name,
    email = this.email,
    password = this.password,
    phone = this.phone
)