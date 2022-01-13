package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.domain.entities.copyAddingUsers
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.roomMock
import com.github.eliascoelho911.youplay.userMock
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
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

    @InjectMockKs
    private lateinit var enterTheRoom: EnterTheRoom

    @Test
    fun testEnterTheRoom() {
        val user = userMock
        val roomId = "roomId"

        every { getLoggedUser.get() } returns flowOf(Resource.success(user))
        coEvery { putCurrentRoomId.put(roomId) } returns Unit
        coEvery { updateCurrentRoom.update(any()) } returns Unit

        runBlocking { enterTheRoom.enter(roomId) }

        coVerify { putCurrentRoomId.put(roomId) }

        verifyUpdateCurrentRoomAddingUser(user)
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