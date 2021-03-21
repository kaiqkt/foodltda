package authorizationservice.resources.redis

import authorizationservice.domain.entities.AuthSessionDetail
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration

@Configuration
class RedisConfig {
    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration("localhost", 6379)
        return JedisConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, AuthSessionDetail> {
        val redisTemplate: RedisTemplate<String, AuthSessionDetail> = RedisTemplate<String, AuthSessionDetail>()
        redisTemplate.setConnectionFactory(jedisConnectionFactory())
        return redisTemplate
    }
}