package authorizationservice

import authorizationservice.domain.entities.AuthSessionDetail
import authorizationservice.resources.security.JWTUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@SpringBootApplication
class AuthorizationServiceApplication


fun main(args: Array<String>) {
    runApplication<AuthorizationServiceApplication>(*args)
}
