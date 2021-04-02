package singleregistry.resources.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import singleregistry.domain.repositories.PersonRepository

@Service
class UserDetailsServiceImpl(private val personRepository: PersonRepository): UserDetailsService {

    override fun loadUserByUsername(email: String?): UserDetails {
        val user = personRepository.findByEmail(email) ?: throw UsernameNotFoundException("User:$email not found")

        return UserDetailsImpl(user)
    }
}