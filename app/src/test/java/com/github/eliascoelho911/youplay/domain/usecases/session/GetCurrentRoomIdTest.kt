package com.github.eliascoelho911.youplay.domain.usecases.session

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentRoomIdTest: BaseTest() {
    @MockK
    private lateinit var applicationSession: ApplicationSession

    @InjectMockKs
    private lateinit var getCurrentRoomId: GetCurrentRoomId

    @Test
    fun testGetCurrentRoomId() {
        val roomId = "roomId"
        coEvery { applicationSession.getCurrentRoomId() } returns roomId

        runBlocking {
            val result = getCurrentRoomId.get()

            assertEquals(roomId, result)
        }
    }
}