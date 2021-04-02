package singleregistry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class SingleRegistryServiceApplication

fun main(args: Array<String>) {
	runApplication<SingleRegistryServiceApplication>(*args)
}
