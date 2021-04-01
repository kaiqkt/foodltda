package authorizationservice

import authorizationservice.domain.entities.AuthSession
import authorizationservice.domain.entities.Channel
import authorizationservice.domain.repositories.RedisSessionRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.core.RedisTemplate
import java.util.*
import java.util.concurrent.TimeUnit

@SpringBootApplication
class AuthorizationServiceApplication(
    private val redisTemplate: RedisTemplate<String, AuthSession>,
    private val redisRepositories: RedisSessionRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        redisTemplate.connectionFactory?.connection?.flushAll()


        val a = AuthSession(
            userId = "23142",
            username = "Test@test.com",
            personId = "12345",
            channel = Channel.APP_MOBILE,
            ip = "10.0.0.7",
            token = UUID.randomUUID().toString(),
            expiration = 10,
            timeUnit = TimeUnit.SECONDS
        )


        redisRepositories.createSession(
            a
        )

    }

}


fun main(args: Array<String>) {
    runApplication<AuthorizationServiceApplication>(*args)
}
