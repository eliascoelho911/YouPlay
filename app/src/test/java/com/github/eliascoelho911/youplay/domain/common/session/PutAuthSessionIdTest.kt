package com.github.eliascoelho911.youplay.domain.common.session

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.common.session.PutAuthSessionId
import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test

class PutAuthSessionIdTest: BaseTest() {
    @MockK
    private lateinit var applicationSession: ApplicationSession

    @InjectMockKs
    private lateinit var putAuthSessionId: PutAuthSessionId

    @Test
    fun testPutAuthSessionId() {
        val id = "id"

        coEvery { applicationSession.putAuthId(id) } returns Unit

        runBlocking {
            putAuthSessionId.put(id)
        }

        coVerify { applicationSession.putAuthId(id) }
    }
}