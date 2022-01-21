package com.github.eliascoelho911.youplay.domain.exceptions

class DomainErrorException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)