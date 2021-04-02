package singleregistry.resources.authorization.gateways

import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import singleregistry.domain.exceptions.CommunicationException
import singleregistry.resources.authorization.clients.AuthorizationServiceClient
import singleregistry.resources.authorization.entities.User

@Service
class AuthorizationServiceImpl(
    private val authorizationServiceClient: AuthorizationServiceClient,
    @Value("\${authorization_token}") private var token: String
) {

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(AuthorizationServiceImpl::class.java.name)
    }

    fun register(user: User) {
        try {
            authorizationServiceClient.register(user, token)
        } catch (ex: FeignException){
            when(ex.status()){
                400 -> logger.error(ex.message)
                403 -> logger.error(ex.message)
                else -> throw CommunicationException("Error when register user")
            }
        }
    }
}