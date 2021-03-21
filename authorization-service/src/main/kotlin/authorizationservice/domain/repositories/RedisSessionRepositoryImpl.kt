package authorizationservice.domain.repositories

import authorizationservice.domain.entities.AuthSessionDetail
import authorizationservice.domain.exceptions.SessionException
import authorizationservice.resources.redis.RedisConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.HashOperations
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class RedisSessionRepositoryImpl(redisConfig: RedisConfig): RedisSessionRepository {

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(RedisSessionRepositoryImpl::class.java)
    }

    private var hashOperations: HashOperations<String, String, AuthSessionDetail> = redisConfig.redisTemplate().opsForHash()

    override fun createSession(authDetail: AuthSessionDetail) {
        val key = generateKey(authDetail)
        try {
            hashOperations.put(key, authDetail.personId,  authDetail)
        } catch (ex: Exception) {
            logger.error("Create session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun findSession(authDetail: AuthSessionDetail): AuthSessionDetail? {
        val key = generateKey(authDetail)
        try {
            return hashOperations[key, authDetail.personId]
        } catch (ex: Exception) {
            logger.error("Find session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun deleteSession(authDetail: AuthSessionDetail) {
        val key = generateKey(authDetail)
        try {
            hashOperations.delete(key, authDetail.personId)
        } catch (ex: Exception) {
            logger.error("Delete session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    private fun generateKey(authDetail: AuthSessionDetail) =
        "session_for:${authDetail.channel.trim().toUpperCase()}:${authDetail.username}"
}

