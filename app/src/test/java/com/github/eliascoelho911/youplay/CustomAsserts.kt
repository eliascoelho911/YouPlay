package com.github.eliascoelho911.youplay

import com.github.eliascoelho911.youplay.common.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

fun assertIsResourceLoading(value: Any) = assertTrue(value is Resource.Loading<*>)

fun assertIsResourceSuccess(value: Any, data: Any) {
    assertTrue(value is Resource.Success<*>)
    (value as Resource.Success<*>).run {
        assertEquals(this.data, data)
    }
}

fun assertIsResourceFailure(value: Any, throwable: Throwable) {
    assertTrue(value is Resource.Failure<*>)
    (value as Resource.Failure<*>).run {
        assertEquals(this.throwable, throwable)
    }
}