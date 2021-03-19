package authorizationservice.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class User(
    @Id
    val _id: String? = null,
    val personId: String,
    val name: String,
    val email: String,
    @JsonIgnore
    var password: String,
    val phone: Phone?,
    val createdAt: LocalDateTime = LocalDateTime.now()
)