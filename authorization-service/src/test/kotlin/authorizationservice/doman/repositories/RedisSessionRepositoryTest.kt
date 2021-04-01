package authorizationservice.doman.repositories

import authorizationservice.domain.entities.AuthSession
import authorizationservice.domain.repositories.RedisSessionRepository
import authorizationservice.domain.repositories.RedisSessionRepositoryImpl
import authorizationservice.factories.AuthSessionFactory
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate

class RedisSessionRepositoryTest {
    private lateinit var redisTemplate: RedisTemplate<String, AuthSession>
    private lateinit var redisSessionRepository: RedisSessionRepository
    private lateinit var redisSessionRepositoryImpl: RedisSessionRepositoryImpl
    private lateinit var hashOperations: HashOperations<String, String, AuthSession>

    @BeforeEach
    fun beforeEach() {
        redisTemplate = mockk(relaxed = true)
        redisTemplate.connectionFactory?.connection?.flushAll()
        redisSessionRepository = mockk(relaxed = true)
        hashOperations = redisTemplate.opsForHash()
        redisSessionRepositoryImpl = RedisSessionRepositoryImpl(redisTemplate)
    }


    @Test
    fun `given a valid auth details, should be save in redis storage with successful`() {
        val authDetail = AuthSessionFactory.sample()
        val key = "SOME_KEY"

        every { hashOperations.put(key, authDetail.userId!!, authDetail) } just runs
        every { redisTemplate.expire(key, authDetail.expiration.toLong(), authDetail.timeUnit) } returns true

        redisSessionRepositoryImpl.createSession(authDetail)

        verify { hashOperations.put(any(), any(), any()) }
        verify { redisTemplate.expire(any(), any(), any()) }
        Assertions.assertEquals(0, redisTemplate.getExpire(key, authDetail.timeUnit))
    }

    @Test
    fun `given a person id, should be find session in redis store with successful`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()

        every { hashOperations.get(key, authDetail.userId!!) } returns authDetail

        redisSessionRepositoryImpl.findSession(authDetail.channel, authDetail.username, authDetail.userId)

        verify { hashOperations.get(any(), any()) }
        Assertions.assertNotNull(
            redisSessionRepositoryImpl.findSession(
                authDetail.channel,
                authDetail.username,
                authDetail.userId
            )
        )
    }

    @Test
    fun `given a error in find session, should be throw Exception`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()

        every { hashOperations.get(key, authDetail.userId!!) } throws Exception()

        assertThrows<Exception> {
            redisSessionRepositoryImpl.findSession(authDetail.channel, authDetail.username, authDetail.userId)
        }

        verify { hashOperations.get(any(), any()) }
    }

    @Test
    fun `given a id is not in database, should be return null`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()

        every { hashOperations.get(key, authDetail.userId!!) } returns null

        redisSessionRepositoryImpl.findSession(authDetail.channel, authDetail.username, authDetail.userId)

        verify { hashOperations.get(any(), any()) }
        Assertions.assertNull(
            redisSessionRepositoryImpl.findSession(
                authDetail.channel,
                authDetail.username,
                authDetail.userId
            )
        )
    }

    @Test
    fun `given a key, should be find sessions in redis store with successful`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()
        val sessions = mapOf(authDetail.userId!! to authDetail)

        every { hashOperations.entries(key) } returns sessions

        redisSessionRepositoryImpl.findSessions(authDetail.channel, authDetail.username)

        verify { hashOperations.entries(any()) }
        Assertions.assertNotNull(redisSessionRepositoryImpl.findSessions(authDetail.channel, authDetail.username))
    }

    @Test
    fun `given a error in find sessions, should be throw Exception`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()

        every { hashOperations.entries(key) } throws Exception()

        assertThrows<Exception> {
            redisSessionRepositoryImpl.findSessions(authDetail.channel, authDetail.username)
        }

        verify { hashOperations.entries(any()) }
    }

    @Test
    fun `given a person id, should be delete session in redis store with successful`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()

        every { hashOperations.delete(key, authDetail.userId) } returns 1

        redisSessionRepositoryImpl.deleteSession(authDetail.channel, authDetail.username, authDetail.userId)

        verify { hashOperations.delete(any(), any()) }
    }

    @Test
    fun `given a error in delete session, should be throw Exception`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()

        every { hashOperations.delete(key, authDetail.userId) } throws Exception()

        assertThrows<Exception> {
            redisSessionRepositoryImpl.deleteSession(authDetail.channel, authDetail.username, authDetail.userId)
        }

        verify { hashOperations.delete(any(), any()) }
    }

    @Test
    fun `given a key, should be delete sessions in redis store with successful`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()

        every { redisTemplate.delete(key) } returns true

        redisSessionRepositoryImpl.deleteSessions(authDetail.channel, authDetail.username)

        verify { redisTemplate.delete(key) }
    }

    @Test
    fun `given a error, should be throw Exception`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionFactory.sample()

        every { redisTemplate.delete(key) } throws Exception()

        assertThrows<Exception> {
            redisSessionRepositoryImpl.deleteSessions(authDetail.channel, authDetail.username)
        }

        verify { redisTemplate.delete(key) }
    }

}