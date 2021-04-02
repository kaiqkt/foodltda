package authorizationservice.domain.repositories

import authorizationservice.domain.entities.AuthSession

interface RedisSessionRepository {
    fun createSession(auth: AuthSession)
    fun findSession(userId: String?): AuthSession?
    fun deleteSession(userId: String?)
}