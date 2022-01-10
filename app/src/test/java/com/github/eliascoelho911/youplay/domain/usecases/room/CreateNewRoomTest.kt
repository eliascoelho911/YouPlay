package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CreateNewRoomTest {
    @RelaxedMockK
    private lateinit var roomRepository: RoomRepository

    @MockK
    private lateinit var getLoggedUser: GetLoggedUser

    @InjectMockKs
    private lateinit var createNewRoom: CreateNewRoom

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `deve criar uma nova sala quando encontrar loggedUser`() {
        val ownerIdExpected = "ownerId"
        val roomIdExpected = "roomId"
        val roomNameExpected = "roomName"
        val userMock: User = mockk {
            every { id } returns ownerIdExpected
        }

        every { getLoggedUser.loggedUser } returns flowOf(Resource.success(userMock))

        runBlocking {
            createNewRoom.invoke(roomIdExpected, roomNameExpected)
        }

        val roomCreated = CapturingSlot<Room>()
        coVerify { roomRepository.add(capture(roomCreated)) }

        assertEquals(ownerIdExpected, roomCreated.captured.ownerId)
        assertEquals(roomIdExpected, roomCreated.captured.id)
        assertEquals(roomNameExpected, roomCreated.captured.name)
    }

    @Test(expected = NoSuchElementException::class)
    fun `deve lancar erro quando nao encontrar o loggedUser`() {
        every { getLoggedUser.loggedUser } returns flowOf(Resource.failure(RuntimeException()))

        runBlocking { createNewRoom.invoke("id", "name") }
    }
}