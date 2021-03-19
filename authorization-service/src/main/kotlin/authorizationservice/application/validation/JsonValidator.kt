package authorizationservice.application.validation

import authorizationservice.domain.exceptions.ResultBindingException
import org.springframework.validation.BindingResult

object JsonValidator {
    fun validate(result: BindingResult){
        val errors = mutableListOf<String>()

        if (result.hasErrors()){
            result.allErrors.forEach { error ->
                error.defaultMessage?.let { errors.add(it) }
            }
            throw ResultBindingException(errors)
        }
    }
}