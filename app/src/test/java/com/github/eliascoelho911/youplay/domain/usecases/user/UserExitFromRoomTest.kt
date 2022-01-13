package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.domain.usecases.room.DeleteRoomById
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateRoom
import com.github.eliascoelho911.youplay.roomMock
import com.github.eliascoelho911.youplay.userMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UserExitFromRoomTest : BaseTest() {
    @MockK
    private lateinit var getCurrentRoom: GetCurrentRoom

    @MockK
    private lateinit var getLoggedUser: GetLoggedUser

    @MockK
    private lateinit var deleteRoomById: DeleteRoomById

    @MockK
    private lateinit var updateRoom: UpdateRoom

    @InjectMockKs
    private lateinit var userExitFromRoom: UserExitFromRoom

    @Test
    fun testDeveDeletarSalaQuandoUsuarioLogadoForDonoDaSala() {
        val loggedUser = userMock
        val room = roomMock.copy(ownerId = loggedUser.id)

        every { getCurrentRoom.get() } returns flowOf(Resource.success(room))
        every { getLoggedUser.get() } returns flowOf(Resource.success(loggedUser))
        coEvery { deleteRoomById.delete(room.id) } returns Unit

        runBlocking { userExitFromRoom.exit() }

        coVerify { deleteRoomById.delete(room.id) }
    }

    @Test
    fun testDeveSairDaSalaQuandoUsuarioLogadoNaoForDonoDaSala() {
        val loggedUser = userMock
        val room = roomMock
        val roomWithoutLoggedUser = room.copy(users = emptyList())

        every { getCurrentRoom.get() } returns flowOf(Resource.success(room))
        every { getLoggedUser.get() } returns flowOf(Resource.success(loggedUser))
        coEvery { updateRoom.update(roomWithoutLoggedUser) } returns Unit

        runBlocking { userExitFromRoom.exit() }

        coVerify { updateRoom.update(roomWithoutLoggedUser) }
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
}