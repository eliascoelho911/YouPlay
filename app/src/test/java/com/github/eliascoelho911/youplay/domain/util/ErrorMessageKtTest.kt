package com.github.eliascoelho911.youplay.domain.util

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.assertIsResourceFailure
import com.github.eliascoelho911.youplay.domain.exceptions.DomainErrorException
import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.global.lastResult
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ErrorMessageKtTest : BaseTest() {
    @Test
    fun testRunChangingExceptionMessage() {
        val message = "message"
        val cause = RuntimeException()
        runCatching {
            runChangingExceptionMessage(message) {
                throw cause
            }
        }.onSuccess {
            fail()
        }.onFailure {
            assertEquals(cause, it.cause)
            assertEquals(message, it.message)
            assertThat(it, instanceOf(DomainErrorException::class.java))
        }
    }

    @Test
    fun testChangeFailureMessage() {
        val throwable = RuntimeException()
        val newMessage = "newMessage"
        val flow = flowOf(Resource.failure<Any>(throwable))

        runBlocking {
            val result = flow.changeFailureMessage(newMessage).lastResult()
            assertIsResourceFailure(result, DomainErrorException::class.java)
            assertEquals(newMessage, (result as Resource.Failure).throwable.message)
        }
    }
}