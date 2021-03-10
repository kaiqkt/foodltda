package singleregistry.resources.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Value("\${secret}")
    private lateinit var secret: String

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        print(secret)
        http.cors().and().csrf().disable()
        http.authorizeRequests()
            .anyRequest().authenticated()
        http.addFilter(AuthorizationFilter(authenticationManager(), secret))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val cors = CorsConfiguration().applyPermitDefaultValues()
        cors.allowedMethods = Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", cors)
        return source
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
