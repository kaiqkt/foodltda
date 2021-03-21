package authorizationservice.domain.repositories

import authorizationservice.domain.entities.AuthSessionDetail
import authorizationservice.resources.redis.RedisConfig
import org.springframework.stereotype.Repository
import org.springframework.data.redis.core.HashOperations

@Repository
class RedisSessionRepositoryImpl(redisConfig: RedisConfig): RedisSessionRepository {

    private var hashOperations: HashOperations<String, String, AuthSessionDetail> = redisConfig.redisTemplate().opsForHash()


    override fun createSession(authDetail: AuthSessionDetail) {
        val key = generateKey(authDetail)
        hashOperations.put(key, authDetail.personId,  authDetail)
    }

    override fun findSession(authDetail: AuthSessionDetail): AuthSessionDetail? {
        val key = generateKey(authDetail)
        return hashOperations[key, authDetail.personId]
    }

    override fun deleteSession(authDetail: AuthSessionDetail) {
        val key = generateKey(authDetail)
        hashOperations.delete(key, authDetail.personId)
    }

    private fun generateKey(authDetail: AuthSessionDetail) =
        "session_for:${authDetail.channel.trim().toUpperCase()}:${authDetail.username}"
}

