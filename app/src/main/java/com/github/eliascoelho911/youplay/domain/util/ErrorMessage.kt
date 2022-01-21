package com.github.eliascoelho911.youplay.domain.util

import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.domain.exceptions.DomainErrorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T> runChangingExceptionMessage(message: String, block: () -> T) = runCatching {
    block()
}.getOrElse { throwable ->
    throw if (throwable is DomainErrorException)
        throwable
    else
        DomainErrorException(message, cause = throwable)
}

fun <T> Flow<Resource<T>>.changeFailureMessage(message: String): Flow<Resource<T>> = map {
    if (it is Resource.Failure<*>) {
        Resource.failure(DomainErrorException(message, cause = it.throwable))
    } else {
        it
    }
}