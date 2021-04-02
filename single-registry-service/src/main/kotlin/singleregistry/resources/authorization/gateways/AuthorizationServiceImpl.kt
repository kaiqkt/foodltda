package singleregistry.resources.authorization.gateways

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import singleregistry.resources.authorization.entities.User
import singleregistry.resources.authorization.clients.AuthorizationServiceClient

@Service
class AuthorizationServiceImpl(
    private val authorizationServiceClient: AuthorizationServiceClient,
    @Value("\${authorization_token}") private var token: String
) {


    fun register(user: User) {
        authorizationServiceClient.register(user, token)
    }
}