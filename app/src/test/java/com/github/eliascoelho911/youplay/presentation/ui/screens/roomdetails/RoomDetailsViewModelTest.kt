package com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails

import android.content.Context
import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.UserExitFromRoom
import com.github.eliascoelho911.youplay.roomMock
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RoomDetailsViewModelTest : BaseTest() {
    @MockK
    private lateinit var userExitFromRoom: UserExitFromRoom

    @MockK
    private lateinit var updateCurrentRoom: UpdateCurrentRoom

    @MockK
    private lateinit var observeCurrentRoom: ObserveCurrentRoom

    @MockK
    private lateinit var observeCurrentMusic: ObserveCurrentMusic

    @RelaxedMockK
    private lateinit var context: Context

    @InjectMockKs
    private lateinit var viewModel: RoomDetailsViewModel

    @Test
    fun testUpdateCurrentName() {
        val name = "newName"
        val currentRoom = roomMock

        coEvery { updateCurrentRoom.update(any()) } returns Unit

        runBlocking {
            viewModel.updateCurrentRoomName(name)
        }

        val roomModificationSlot = CapturingSlot<suspend Room.() -> Room>()
        coVerify { updateCurrentRoom.update(capture(roomModificationSlot)) }

        runBlocking {
            val expected = roomModificationSlot.captured.invoke(currentRoom)
            assertEquals(expected, currentRoom.copy(name = name))
        }
    }
}