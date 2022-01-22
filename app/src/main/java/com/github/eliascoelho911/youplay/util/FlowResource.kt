package com.github.eliascoelho911.youplay.util

import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

fun <T> flowResource(block: suspend FlowCollector<Resource<T>>.() -> Unit): Flow<Resource<T>> =
    flow {
        emit(Resource.loading())
        block()
    }.distinctUntilChanged()

fun <T> callbackFlowResource(block: suspend ProducerScope<Resource<T>>.() -> Unit): Flow<Resource<T>> =
    callbackFlow {
        send(Resource.loading())
        block()
    }.distinctUntilChanged()

suspend inline fun <T> Flow<Resource<T>>.collectResource(crossinline action: suspend Resource<T>.() -> Unit): Unit =
    collect { action(it) }

suspend fun <T> Flow<Resource<T>>.lastResult(): Resource<T> {
    var result: Resource<T>? = null
    collect {
        if ((it is Resource.Success) or (it is Resource.Failure))
            result = it
    }

    if (result == null) throw NoSuchElementException("Expected at least one element")

    return result!!
}

fun <T> Flow<Resource<T>>.catchExceptions() =
    catch { throwable ->
        emit(Resource.failure(throwable))
    }

suspend fun <T> FlowCollector<Resource<T>>.emitSuccess(
    value: T,
) {
    emit(Resource.success(value))
}

suspend fun <T> FlowCollector<Resource<T>>.emitLoading() {
    emit(Resource.loading())
}

suspend fun <T> FlowCollector<Resource<T>>.emitFailure(throwable: Throwable) {
    emit(Resource.failure(throwable))
}