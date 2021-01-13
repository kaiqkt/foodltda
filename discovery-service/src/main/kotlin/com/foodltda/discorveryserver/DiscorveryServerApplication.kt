package com.foodltda.discorveryserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class DiscorveryServerApplication

fun main(args: Array<String>) {
	runApplication<DiscorveryServerApplication>(*args)
}
