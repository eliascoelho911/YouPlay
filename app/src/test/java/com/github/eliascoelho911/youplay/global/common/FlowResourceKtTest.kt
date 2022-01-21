package com.github.eliascoelho911.youplay.global.common

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.assertIsResourceFailure
import com.github.eliascoelho911.youplay.assertIsResourceLoading
import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.global.callbackFlowResource
import com.github.eliascoelho911.youplay.global.catchExceptions
import com.github.eliascoelho911.youplay.global.flowResource
import com.github.eliascoelho911.youplay.global.lastResult
import io.mockk.mockk
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FlowResourceKtTest : BaseTest() {
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
            val flow = flowResource<Unit> { throw throwable }.catchExceptions()

            assertIsResourceFailure(flow.lastResult(), Throwable::class.java)
        }
    }
}