package com.github.eliascoelho911.youplay.domain.usecases.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PutCurrentRoomIdTest {
    @MockK
    private lateinit var applicationSession: ApplicationSession

    @InjectMockKs
    private lateinit var putCurrentRoomId: PutCurrentRoomId

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun putCurrentRoomId() {
        val id = "id"

        runBlocking {
            putCurrentRoomId.invoke(id)
        }

        coVerify { applicationSession.putAuthId(id) }
    }
}