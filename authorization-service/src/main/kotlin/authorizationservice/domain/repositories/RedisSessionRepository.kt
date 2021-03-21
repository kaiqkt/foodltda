package authorizationservice.domain.repositories

import authorizationservice.domain.entities.AuthSessionDetail

interface RedisSessionRepository {
    fun createSession(authDetail: AuthSessionDetail)
    fun findSession(authDetail: AuthSessionDetail): AuthSessionDetail?
}