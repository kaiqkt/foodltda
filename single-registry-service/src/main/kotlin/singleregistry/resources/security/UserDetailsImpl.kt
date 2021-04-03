package singleregistry.resources.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import singleregistry.domain.entities.person.Person

class UserDetailsImpl(private val person: Person) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = mutableListOf<GrantedAuthority>()
        return authorities
    }

    override fun isEnabled() = true

    override fun getUsername() =  person.email

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = null

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}