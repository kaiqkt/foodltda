package com.zed.restaurantservice.resources.singleregistry.clients

import com.zed.restaurantservice.resources.singleregistry.entities.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "single-registry-service", url = "http://localhost:8083")
interface SingleRegistryClient {

    @GetMapping("/person/{personId}")
    fun findByPersonId(@PathVariable personId: String?,  @RequestHeader("Authorization") accessToken: String): ResponseEntity<Response>
}