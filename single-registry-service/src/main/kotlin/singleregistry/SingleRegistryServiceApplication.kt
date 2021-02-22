package singleregistry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SingleRegistryServiceApplication

fun main(args: Array<String>) {
	runApplication<SingleRegistryServiceApplication>(*args)
}
