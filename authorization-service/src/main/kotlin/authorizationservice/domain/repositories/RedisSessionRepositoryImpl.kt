package authorizationservice.domain.repositories

import authorizationservice.domain.entities.AuthSession
import authorizationservice.domain.entities.Channel
import authorizationservice.domain.exceptions.SessionException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisSessionRepositoryImpl (private val redisTemplate: RedisTemplate<String, AuthSession>) :
    RedisSessionRepository {

    private var hashOperations: HashOperations<String, String, AuthSession> = redisTemplate.opsForHash()

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(RedisSessionRepositoryImpl::class.java)
    }

    override fun createSession(auth: AuthSession) {
        val key = generateKey(auth.channel, auth.username)
        val expiration = auth.expiration
        try {
            hashOperations.put(key, auth.userId!!, auth)
            redisTemplate.expire(key, expiration, TimeUnit.HOURS)
        } catch (ex: Exception) {
            logger.error("Create session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun findSession(channel: Channel, username: String?, userId: String?): AuthSession? {
        val key = generateKey(channel, username)
        try {
            return hashOperations.get(key,userId!!)
        } catch (ex: Exception) {
            logger.error("Find session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun findSessions(channel: Channel, username: String?): MutableMap<String, AuthSession> {
        val key = generateKey(channel, username)
        return try {
            hashOperations.entries(key)
        } catch (ex: Exception) {
            logger.error("Delete sessions error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun deleteSession(channel: Channel, username: String?, userId: String?) {
        val key = generateKey(channel, username)
        try {
            hashOperations.delete(key, userId)
        } catch (ex: Exception) {
            logger.error("Delete session error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    override fun deleteSessions(channel: Channel, username: String?) {
        val key = generateKey(channel, username)
        try {
           redisTemplate.delete(key)
        } catch (ex: Exception) {
            logger.error("Delete sessions error [key:$key]")
            throw SessionException(ex.message)
        }
    }

    private fun generateKey(channel: Channel, username: String?) =
        "session_for:${channel.name.toUpperCase()}:${username}"
}

