package com.github.eliascoelho911.youplay

import com.github.eliascoelho911.youplay.common.Resource
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertEquals

fun assertIsResourceLoading(value: Any) =
    assertThat(value, instanceOf(Resource.Loading::class.java))

fun assertIsResourceSuccess(value: Any, data: Any) {
    assertThat(value, instanceOf(Resource.Success::class.java))
    (value as Resource.Success<*>).run {
        assertEquals(this.data, data)
    }
}

fun assertIsResourceFailure(value: Any, throwableClass: Class<*>? = null) {
    assertThat(value, instanceOf(Resource.Failure::class.java))
    if (throwableClass != null)
        (value as Resource.Failure<*>).run {
            assertThat(this.throwable, instanceOf(throwableClass))
        }
}