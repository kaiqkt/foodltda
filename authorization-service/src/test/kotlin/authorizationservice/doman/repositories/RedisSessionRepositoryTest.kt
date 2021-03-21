package authorizationservice.doman.repositories

import authorizationservice.domain.entities.AuthSessionDetail
import authorizationservice.domain.repositories.RedisSessionRepository
import authorizationservice.domain.repositories.RedisSessionRepositoryImpl
import authorizationservice.factories.AuthSessionDetailFactory
import authorizationservice.resources.redis.RedisConfig
import io.mockk.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.redis.core.HashOperations

class RedisSessionRepositoryTest {
    private lateinit var redisConfig: RedisConfig
    private lateinit var redisSessionRepository: RedisSessionRepository
    private lateinit var redisSessionRepositoryImpl: RedisSessionRepositoryImpl
    private lateinit var hashOperations: HashOperations<String, String, AuthSessionDetail>

    @BeforeEach
    fun beforeEach() {
        redisConfig = mockk(relaxed = true)
        redisSessionRepository = mockk(relaxed = true)
        hashOperations = redisConfig.redisTemplate().opsForHash()
        redisSessionRepositoryImpl = RedisSessionRepositoryImpl(redisConfig)
    }

    @Test
    fun `given a valid auth details, should be save in redis store with successful`() {
        val authDetail = AuthSessionDetailFactory.sample()

        every { redisSessionRepository.createSession(authDetail) } just runs

        redisSessionRepositoryImpl.createSession(authDetail)

        verify { redisConfig.redisTemplate() }
    }

    @Test
    fun `given a error in put auth detail, should be return Exception`() {
        val authDetail = AuthSessionDetailFactory.sample()

        every { redisSessionRepository.createSession(authDetail) } throws Exception()

        assertThrows<Exception> {
            redisSessionRepository.createSession(authDetail)
        }

        verify { redisConfig.redisTemplate() }
    }

    @Test
    fun `given a person id, should be find session in redis store with successful`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionDetailFactory.sample()

        every { hashOperations[key, "12345"] } returns authDetail

        redisSessionRepositoryImpl.findSession(authDetail)

        verify { hashOperations[any(), any()] }
        Assertions.assertNotNull(redisSessionRepositoryImpl.findSession(authDetail))
    }

    @Test
    fun `given a error in find session, should be return Exception`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionDetailFactory.sample()

        every { hashOperations[key, "12345"] } returns null

        redisSessionRepositoryImpl.findSession(authDetail)

        verify { hashOperations[any(), any()] }
        Assertions.assertNull(redisSessionRepositoryImpl.findSession(authDetail))
    }

    @Test
    fun `given a person id, should be delete session in redis store with successful`() {
        val key = "session_for:APP_MOBILE:Test@test.com"
        val authDetail = AuthSessionDetailFactory.sample()

        every { hashOperations[key, "12345"] } returns authDetail

        redisSessionRepositoryImpl.findSession(authDetail)

        verify { hashOperations[any(), any()] }
        Assertions.assertNotNull(redisSessionRepositoryImpl.findSession(authDetail))
    }
}