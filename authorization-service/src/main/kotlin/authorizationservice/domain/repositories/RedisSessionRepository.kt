package authorizationservice.domain.repositories

import authorizationservice.domain.entities.AuthSession
import authorizationservice.domain.entities.Channel

interface RedisSessionRepository {
    fun createSession(auth: AuthSession)
    fun findSession(channel: Channel, username: String?, userId: String?): AuthSession?
    fun findSessions(channel: Channel, username: String?): MutableMap<String, AuthSession>
    fun deleteSession(channel: Channel, username: String?, userId: String?)
    fun deleteSessions(channel: Channel, username: String?)
}