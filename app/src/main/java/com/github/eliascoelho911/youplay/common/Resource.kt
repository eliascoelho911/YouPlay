package com.github.eliascoelho911.youplay.common

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

    inline fun onSuccess(block: (data: T) -> Unit): Resource<T> {
        if (this is Success)
            block(this.data)
        return this
    }

    inline fun onFailure(block: (throwable: Throwable) -> Unit): Resource<T> {
        if (this is Failure)
            block(this.throwable)
        return this
    }

    inline fun onLoading(block: () -> Unit): Resource<T> {
        if (this is Loading)
            block()
        return this
    }
}