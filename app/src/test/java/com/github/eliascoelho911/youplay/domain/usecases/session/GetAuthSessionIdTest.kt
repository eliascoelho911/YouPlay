package com.github.eliascoelho911.youplay.domain.usecases.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAuthSessionIdTest {
    @RelaxedMockK
    private lateinit var applicationSession: ApplicationSession

    @InjectMockKs
    private lateinit var getAuthSessionId: GetAuthSessionId

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getSessionId() {
        val id = "id"
        coEvery { applicationSession.getId() } returns id

        runBlocking {
            assertEquals(id, getAuthSessionId.get())
        }
    }
}