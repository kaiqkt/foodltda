package authorizationservice.domain.repositories

import authorizationservice.domain.entities.AuthSessionDetail
import authorizationservice.domain.exceptions.SessionException
import authorizationservice.resources.redis.RedisConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.HashOperations
import org.springframework.stereotype.Repository

@Repository
class RedisSessionRepositoryImpl(
    private val redisConfig: RedisConfig,
) : RedisSessionRepository {

    private var hashOperations: HashOperations<String, String, AuthSessionDetail> = redisConfig.redisTemplate().opsForHash()

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(RedisSessionRepositoryImpl::class.java)
    }

    override fun createSession(authDetail: AuthSessionDetail) {
        val key = generateKey(authDetail)
        try {
            hashOperations.put(key, authDetail.personId, authDetail)
            redisConfig.redisTemplate().expire(key, authDetail.expiration, authDetail.timeUnit)
        } catch (ex: Exception) {
            logger.error("Create session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun findSession(authDetail: AuthSessionDetail): AuthSessionDetail? {
        val key = generateKey(authDetail)
        return hashOperations[key, authDetail.personId]
    }

    private fun generateKey(authDetail: AuthSessionDetail) =
        "session_for:${authDetail.channel.trim().toUpperCase()}:${authDetail.username}"

}

