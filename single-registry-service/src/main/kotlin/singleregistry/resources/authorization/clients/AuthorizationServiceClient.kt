package singleregistry.resources.authorization.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import singleregistry.resources.authorization.entities.User


@FeignClient(name = "authorization-service", url = "http://localhost:8084")
interface AuthorizationServiceClient {

    @PostMapping("/users")
    fun register(@RequestBody user: User, @RequestHeader("Authorization") accessToken: String): ResponseEntity<User>

}