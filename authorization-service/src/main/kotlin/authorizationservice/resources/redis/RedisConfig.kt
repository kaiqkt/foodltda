package authorizationservice.resources.redis

import authorizationservice.domain.entities.AuthSession
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer

@Configuration
@ComponentScan("com.javasampleapproach.redis")
class RedisConfig {
    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration("localhost", 6379)
        return JedisConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, AuthSession> {
        val redisTemplate: RedisTemplate<String, AuthSession> = RedisTemplate<String, AuthSession>()
        redisTemplate.setConnectionFactory(jedisConnectionFactory())
        redisTemplate.valueSerializer = GenericToStringSerializer(Any::class.java)
        return redisTemplate
    }
}