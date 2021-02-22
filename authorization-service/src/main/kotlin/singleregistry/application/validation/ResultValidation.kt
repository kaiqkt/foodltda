package singleregistry.application.validation

import org.springframework.validation.BindingResult
import singleregistry.domain.exceptions.ResultBindingException

object JsonValidator {
    fun validate(result: BindingResult?){
        val errors = mutableListOf<String>()

        if (result?.hasErrors() == true){
            result.allErrors.forEach { error ->
                error.defaultMessage?.let { errors.add(it) }
            }
            throw ResultBindingException(errors)
        }
    }
}