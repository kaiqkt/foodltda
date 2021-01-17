package com.foodltda.merchantservice.domain.exceptions

import com.foodltda.merchantservice.application.dto.response.Response
import com.foodltda.merchantservice.domain.entities.LegalPerson
import java.lang.Exception

class ResultBindingException(response: Response<Any>): Exception(response.errors.toString())
