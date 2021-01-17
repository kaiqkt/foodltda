package com.foodltda.merchantservice.domain.validation

import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.exceptions.ResultBindingException
import org.springframework.validation.BindingResult

object ResultValidation {
    fun check(response: Response<Any>, result: BindingResult): Response<Any> {

        if (result.hasErrors()){
            result.allErrors.forEach { error ->
                error.defaultMessage?.let { response.errors.add(it) }
            }
            throw ResultBindingException(response)
        }
        return response
    }
}