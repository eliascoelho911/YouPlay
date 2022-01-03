package com.github.eliascoelho911.youplay.common

import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

//todo criar testes
fun <T> flowResource(block: suspend FlowCollector<Resource<T>>.() -> Unit): Flow<Resource<T>> =
    flow {
        emit(Resource.loading())
        block()
    }.distinctUntilChanged()

fun <T> callbackFlowResource(block: suspend ProducerScope<Resource<T>>.() -> Unit): Flow<Resource<T>> =
    callbackFlow {
        trySend(Resource.loading())
        block()
    }.distinctUntilChanged()

suspend inline fun <T> Flow<Resource<T>>.collectResource(crossinline action: suspend Resource<T>.() -> Unit): Unit =
    collect { action(it) }

suspend fun <T> Flow<Resource<T>>.lastResult(): Resource<T> {
    var result: Resource<T>? = null
    collect {
        if ((it is Resource.Success) or (it is Resource.Failed))
            result = it
    }

    if (result == null) throw NoSuchElementException("Expected at least one element")

    return result!!
}

suspend fun <T> FlowCollector<Resource<T>>.emitIfLoadingOrFailure(
    resource: Resource<*>,
) {
    resource.onLoading {
        emit(Resource.loading())
    }.onFailure { throwable ->
        emit(Resource.failure(throwable))
    }
}

fun <T> Flow<Resource<T>>.emitErrors() =
    catch { throwable ->
        emit(Resource.failure(throwable))
    }

suspend fun <T> FlowCollector<Resource<T>>.emitSuccess(
    value: T,
) {
    emit(Resource.success(value))
}