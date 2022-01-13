package com.github.eliascoelho911.youplay.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.eliascoelho911.youplay.assertIsResourceFailure
import com.github.eliascoelho911.youplay.assertIsResourceLoading
import io.mockk.mockk
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class FlowResourceKtTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testDeveSempreEmitirLoadingAoCriarUmFlowResource() {
        val flowBuilder: suspend (FlowCollector<Resource<Unit>>) -> Unit = mockk()

        val flow = flowResource(flowBuilder)

        runBlocking {
            assertIsResourceLoading(flow.first())
        }
    }

    @Test
    fun testDeveSempreEmitirLoadingAoCriarUmCallbackFlowResource() {
        val flow = callbackFlowResource<Unit> {
            awaitClose {}
        }

        runBlocking {
            assertIsResourceLoading(flow.first())
        }
    }

    @Test
    fun testDeveBuscarUltimoSuccessOuFailedQuandoChamarOnLastResult() {
        val lastResultExpected = Resource.success(Unit)
        val flow = flowOf(Resource.loading(), Resource.Failure(Throwable()), lastResultExpected)

        runBlocking {
            assertEquals(lastResultExpected, flow.lastResult())
        }
    }

    @Test
    fun testDeveEmitResourceFailureQuandoErro() {
        runBlocking {
            val throwable = Throwable()
            val flow = flowResource<Unit> { throw throwable }.emitErrors()

            assertIsResourceFailure(flow.lastResult(), throwable)
        }
    }
}