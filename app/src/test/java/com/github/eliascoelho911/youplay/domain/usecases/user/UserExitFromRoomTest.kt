package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.entities.copyRemovingUsers
import com.github.eliascoelho911.youplay.domain.common.room.DeleteCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.util.Messages
import com.github.eliascoelho911.youplay.roomMock
import com.github.eliascoelho911.youplay.userMock
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

class UserExitFromRoomTest : BaseTest() {
    @MockK
    private lateinit var getCurrentRoom: GetCurrentRoom

    @MockK
    private lateinit var getLoggedUser: GetLoggedUser

    @MockK
    private lateinit var deleteCurrentRoom: DeleteCurrentRoom

    @MockK
    private lateinit var updateCurrentRoom: UpdateCurrentRoom

    @MockK
    private lateinit var putCurrentRoomId: PutCurrentRoomId

    @RelaxedMockK
    private lateinit var errorMessages: Messages.Error

    @InjectMockKs
    private lateinit var userExitFromRoom: UserExitFromRoom

    @Test
    fun testDeveDeletarSalaQuandoUsuarioLogadoForDonoDaSala() {
        val loggedUser = userMock
        val room = roomMock.copy(ownerId = loggedUser.id)

        every { getCurrentRoom.get() } returns flowOf(Resource.success(room))
        every { getLoggedUser.get() } returns flowOf(Resource.success(loggedUser))
        coEvery { deleteCurrentRoom.delete() } returns Unit
        coEvery { putCurrentRoomId.put(any()) } returns Unit

        runBlocking { userExitFromRoom.exit() }

        coVerify { deleteCurrentRoom.delete() }
        coVerify { putCurrentRoomId.put(null) }
    }

    @Test
    fun testDeveSairDaSalaQuandoUsuarioLogadoNaoForDonoDaSala() {
        val loggedUser = userMock
        val room = roomMock
        val roomWithoutLoggedUser = room.copyRemovingUsers(loggedUser.id)

        every { getCurrentRoom.get() } returns flowOf(Resource.success(room))
        every { getLoggedUser.get() } returns flowOf(Resource.success(loggedUser))
        coEvery { updateCurrentRoom.update(any()) } returns Unit
        coEvery { putCurrentRoomId.put(any()) } returns Unit

        runBlocking { userExitFromRoom.exit() }

        coVerify { putCurrentRoomId.put(null) }

        verifyUpdateCurrentRoom(roomWithoutLoggedUser, room)
    }

    @Test(expected = Throwable::class)
    fun testDeveLancarErroQuandoNaoEncontrarSalaAtual() {
        every { getCurrentRoom.get() } returns flowOf(Resource.failure(Throwable()))

        runBlocking { userExitFromRoom.exit() }
    }

    @Test(expected = Throwable::class)
    fun testDeveLancarErroQuandoNaoEncontrarUsuarioLogado() {
        every { getCurrentRoom.get() } returns flowOf(Resource.success(mockk()))
        every { getLoggedUser.get() } returns flowOf(Resource.failure(Throwable()))

        runBlocking { userExitFromRoom.exit() }
    }

    private fun verifyUpdateCurrentRoom(
        roomWithoutLoggedUser: Room,
        room: Room,
    ) {
        val slotRoomModification = CapturingSlot<suspend Room.() -> Room>()
        coVerify { updateCurrentRoom.update(capture(slotRoomModification)) }

        runBlocking {
            assertEquals(roomWithoutLoggedUser, slotRoomModification.captured.invoke(room))
        }
    }
}