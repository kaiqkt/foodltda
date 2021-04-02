package authorizationservice.domain.repositories

import authorizationservice.domain.entities.AuthSession
import authorizationservice.domain.entities.Channel
import authorizationservice.domain.exceptions.SessionException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisSessionRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, AuthSession>,
    @Value("\${redis.key}") private var key: String
) : RedisSessionRepository {

    private var hashOperations: HashOperations<String, String, AuthSession> = redisTemplate.opsForHash()

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(RedisSessionRepositoryImpl::class.java)
    }

    override fun createSession(auth: AuthSession) {
        val expiration = auth.expiration
        try {
            hashOperations.put(key, auth.userId!!, auth)
            redisTemplate.expire(key, expiration, TimeUnit.HOURS)
        } catch (ex: Exception) {
            logger.error("Create session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun findSession(userId: String?): AuthSession? {
        try {
            return hashOperations.get(key, userId!!)
        } catch (ex: Exception) {
            logger.error("Find session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun deleteSession(userId: String?) {
        try {
            hashOperations.delete(key, userId)
        } catch (ex: Exception) {
            logger.error("Delete session error [key:$key]")
            throw SessionException(ex.message)
        }
    }
}

