package com.github.eliascoelho911.youplay.domain.usecases.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
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
    fun testPutCurrentRoomId() {
        val id = "id"

        coEvery { applicationSession.putCurrentRoomId(id) } returns Unit

        runBlocking {
            putCurrentRoomId.put(id)
        }

        coVerify { applicationSession.putCurrentRoomId(id) }
    }
}