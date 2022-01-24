package com.github.eliascoelho911.youplay.domain.usecases.user

import android.content.Context
import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.domain.entities.copyAddingUsers
import com.github.eliascoelho911.youplay.domain.exceptions.DomainErrorException
import com.github.eliascoelho911.youplay.domain.util.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.roomMock
import com.github.eliascoelho911.youplay.userMock
import com.github.eliascoelho911.youplay.util.Resource
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class EnterTheRoomTest : BaseTest() {
    @MockK
    private lateinit var getLoggedUser: GetLoggedUser

    @MockK
    private lateinit var putCurrentRoomId: PutCurrentRoomId

    @MockK
    private lateinit var updateCurrentRoom: UpdateCurrentRoom

    @MockK
    private lateinit var checkIfRoomExistsById: CheckIfRoomExistsById

    @RelaxedMockK
    private lateinit var context: Context

    @InjectMockKs
    private lateinit var enterTheRoom: EnterTheRoom

    @Test
    fun testDeveEntrarNaSalaQuandoElaExistir() {
        val user = userMock
        val roomId = "roomId"

        every { getLoggedUser.get() } returns flowOf(Resource.success(user))
        coEvery { checkIfRoomExistsById.check(roomId) } returns true
        coEvery { putCurrentRoomId.put(roomId) } returns Unit
        coEvery { updateCurrentRoom.update(any()) } returns Unit

        runBlocking { enterTheRoom.enter(roomId) }

        coVerify { putCurrentRoomId.put(roomId) }

        verifyUpdateCurrentRoomAddingUser(user)
    }

    @Test(expected = DomainErrorException::class)
    fun testDeveLancarErroQuandoSalaNaoExistir() {
        val id = "id"

        coEvery { checkIfRoomExistsById.check(id) } returns false

        runBlocking { enterTheRoom.enter(id) }
    }

    private fun verifyUpdateCurrentRoomAddingUser(user: User) {
        val roomModificationSlot = CapturingSlot<suspend Room.() -> Room>()
        coVerify { updateCurrentRoom.update(capture(roomModificationSlot)) }

        val room = roomMock

        runBlocking {
            assertEquals(roomMock.copyAddingUsers(user.id),
                roomModificationSlot.captured.invoke(room))
        }
    }
}