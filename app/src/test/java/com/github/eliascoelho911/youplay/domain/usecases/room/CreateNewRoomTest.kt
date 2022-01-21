package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.domain.exceptions.DomainErrorException
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.domain.util.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.global.Messages
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CreateNewRoomTest: BaseTest() {
    @MockK
    private lateinit var roomRepository: RoomRepository

    @MockK
    private lateinit var getLoggedUser: GetLoggedUser

    @MockK
    private lateinit var checkIfRoomExistsById: CheckIfRoomExistsById

    @RelaxedMockK
    private lateinit var errorMessages: Messages.Error

    @InjectMockKs
    private lateinit var createNewRoom: CreateNewRoom

    @Test
    fun testDeveCriarUmaNovaSalaQuandoEncontrarLoggedUserESalaNaoExistir() {
        val ownerIdExpected = "ownerId"
        val roomIdExpected = "roomId"
        val roomNameExpected = "roomName"
        val userMock: User = mockk {
            every { id } returns ownerIdExpected
        }

        coEvery { checkIfRoomExistsById.check(roomIdExpected) } returns false
        every { getLoggedUser.get() } returns flowOf(Resource.success(userMock))
        coEvery { roomRepository.add(any()) } returns Unit

        runBlocking {
            createNewRoom.create(roomIdExpected, roomNameExpected)
        }

        val roomCreated = CapturingSlot<Room>()
        coVerify { roomRepository.add(capture(roomCreated)) }

        assertEquals(ownerIdExpected, roomCreated.captured.ownerId)
        assertEquals(roomIdExpected, roomCreated.captured.id)
        assertEquals(roomNameExpected, roomCreated.captured.name)
    }

    @Test(expected = DomainErrorException::class)
    fun testDeveLancarErroQuandoSalaJaExistir() {
        val id = "id"

        coEvery { checkIfRoomExistsById.check(id) } returns true

        runBlocking { createNewRoom.create(id, "") }
    }

    @Test(expected = DomainErrorException::class)
    fun testDeveLancarErroQuandoNaoEncontrarOLoggedUser() {
        val id = "id"

        coEvery { checkIfRoomExistsById.check(id) } returns false
        every { getLoggedUser.get() } returns flowOf(Resource.failure(RuntimeException()))

        runBlocking { createNewRoom.create(id, "name") }
    }
}