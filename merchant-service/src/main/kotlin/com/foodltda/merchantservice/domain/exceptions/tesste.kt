package com.foodltda.merchantservice.domain.exceptions

import java.lang.Exception

class CityNotFoundException(id: Long?) : Exception("City with Id %d not found")