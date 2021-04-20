package com.zed.restaurantservice.resources.singleregistry.gateways

import com.zed.restaurantservice.domain.exceptions.CommunicationException
import com.zed.restaurantservice.resources.singleregistry.clients.SingleRegistryClient
import com.zed.restaurantservice.resources.singleregistry.entities.PersonType
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SingleRegistryImpl(
    private val singleRegistryClient: SingleRegistryClient,
    @Value("\${single_registry_token}") private var token: String
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun isLegalPerson(personId: String?): Boolean {
        var isLegal = false

        try {
            singleRegistryClient.findByPersonId(personId, token).let {
                when (it.body?.person?.type) {
                    PersonType.PJ -> isLegal = true
                    else -> isLegal
                }
            }

        } catch (ex: FeignException) {
            when (ex.status()) {
                400 -> log.error(ex.message)
                403 -> log.error(ex.message)
                else -> throw CommunicationException("Error when find person $personId")
            }
        }

        return isLegal
    }
}