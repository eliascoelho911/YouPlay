package com.github.eliascoelho911.youplay.global

sealed class Resource<T> {
    class Loading<T> : Resource<T>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    data class Success<T>(val data: T) : Resource<T>()
    data class Failure<T>(val throwable: Throwable) : Resource<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failure(throwable: Throwable) = Failure<T>(throwable)
    }
}

inline fun <T> Resource<T>.assertSuccess(message: String? = null, whenSuccess: (data: T) -> Unit) {
    val predicate = this is Resource.Success
    if (!predicate) {
        throw if (this is Resource.Failure) {
            AssertionError(message, throwable)
        } else {
            AssertionError(message)
        }
    }
    whenSuccess((this as Resource.Success).data)
}

inline fun <T> Resource<T>.on(
    success: (data: T) -> Unit = {},
    loading: () -> Unit = {},
    failure: (throwable: Throwable) -> Unit = {},
): Resource<T> {
    if (this is Resource.Success)
        success(this.data)
    if (this is Resource.Failure)
        failure(this.throwable)
    if (this is Resource.Loading)
        loading()
    return this
}

inline fun <T> Resource<T>.on(
    success: (data: T) -> Unit = {},
    loadingOrFailure: (Resource<T>) -> Unit = {},
): Resource<T> {
    if (this is Resource.Success)
        success(this.data)
    if ((this is Resource.Loading) or (this is Resource.Failure))
        loadingOrFailure(this)
    return this
}