package com.github.eliascoelho911.youplay.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class FlowResourceKtTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `deve sempre emitir loading ao criar um flowResource`() {
        val flowBuilder: suspend (FlowCollector<Resource<Unit>>) -> Unit = mockk()

        val flow = flowResource(flowBuilder)

        runBlocking {
            assertThat(flow.first(), instanceOf(Resource.Loading<Unit>()::class.java))
        }
    }

    @Test
    fun `deve sempre emitir loading ao criar um callbackFlowResource`() {
        val flow = callbackFlowResource<Unit> {
            awaitClose {}
        }

        runBlocking {
            assertThat(flow.first(), instanceOf(Resource.Loading::class.java))
        }
    }

    @Test
    fun `deve buscar ultimo Success ou Failed quando chamar onLastResult`() {
        val lastResultExpected = Resource.success(Unit)
        val flow = flowOf(Resource.loading(), Resource.Failed(Throwable()), lastResultExpected)

        runBlocking {
            assertThat(flow.lastResult(), equalTo(lastResultExpected))
        }
    }

    @Test
    fun `deve emit ResourceFailure quando erro`() {
        runBlocking {
            val exception = NullPointerException()
            val flow = flowResource<Unit> { throw exception }.emitErrors()

            assertThat(flow.lastResult(), equalTo(Resource.failure(exception)))
        }
    }
}