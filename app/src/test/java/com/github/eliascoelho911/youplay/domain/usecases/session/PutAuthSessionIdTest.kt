package com.github.eliascoelho911.youplay.domain.usecases.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PutAuthSessionIdTest {
    @RelaxedMockK
    private lateinit var applicationSession: ApplicationSession

    @InjectMockKs
    private lateinit var putAuthSessionId: PutAuthSessionId

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun putSessionId() {
        val id = "id"

        runBlocking {
            putAuthSessionId.invoke(id)
        }

        coVerify { applicationSession.putId(id) }
    }
}