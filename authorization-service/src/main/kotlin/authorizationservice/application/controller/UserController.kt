package authorizationservice.application.controller

import authorizationservice.application.dto.UserRequest
import authorizationservice.application.dto.toDomain
import authorizationservice.application.validation.JsonValidator
import authorizationservice.domain.entities.User
import authorizationservice.domain.repositories.RedisSessionRepository
import authorizationservice.domain.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun register(@Valid @RequestBody user: UserRequest, result: BindingResult): ResponseEntity<User>{
        JsonValidator.validate(result)

        return ResponseEntity<User>(userService.create(user.toDomain()), HttpStatus.CREATED)
    }

    @DeleteMapping
    fun logout(): ResponseEntity<Any> {
        userService.deleteSession()

        return ResponseEntity(HttpStatus.OK)
    }
    //update password
}