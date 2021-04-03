package com.zed.restaurantservice.resources.singleregistry.gateways

import com.zed.restaurantservice.domain.exceptions.CommunicationException
import com.zed.restaurantservice.resources.singleregistry.clients.SingleRegistryServiceClient
import com.zed.restaurantservice.resources.singleregistry.entities.Legal
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SingleRegistryServiceImpl(
    private val singleRegistryServiceClient: SingleRegistryServiceClient,
    @Value("\${single_registry_token}") private var token: String
) {
    private companion object {
        val logger: Logger = LoggerFactory.getLogger(SingleRegistryServiceImpl::class.java.name)
    }

    fun findByPersonId(personId: String?): Legal? {
        try {
            val response = singleRegistryServiceClient.findByPersonId(personId, token)
            return response.body
        } catch (ex: FeignException) {
            when (ex.status()) {
                404 -> logger.error(ex.message)
                403 -> logger.error(ex.message)
                else -> throw CommunicationException("Error when find person")
            }
        }
        return null
    }
}