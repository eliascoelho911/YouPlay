package com.github.eliascoelho911.youplay.domain.common.session

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.common.session.GetAuthSessionId
import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAuthSessionIdTest: BaseTest() {
    @MockK
    private lateinit var applicationSession: ApplicationSession

    @InjectMockKs
    private lateinit var getAuthSessionId: GetAuthSessionId

    @Test
    fun testGetAuthSessionId() {
        val id = "id"
        coEvery { applicationSession.getAuthId() } returns id

        runBlocking {
            assertEquals(id, getAuthSessionId.get())
        }
    }
}