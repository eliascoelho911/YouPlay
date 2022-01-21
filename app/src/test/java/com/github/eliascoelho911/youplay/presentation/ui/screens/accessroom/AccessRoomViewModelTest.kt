package com.github.eliascoelho911.youplay.presentation.ui.screens.accessroom

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AccessRoomViewModelTest : BaseTest() {
    @MockK
    private lateinit var enterTheRoom: EnterTheRoom

    @InjectMockKs
    private lateinit var accessRoomViewModel: AccessRoomViewModel

    @Test
    fun testEnterTheRoom() {
        val roomId = "roomId"
        coEvery { enterTheRoom.enter(roomId) } returns Unit

        runBlocking { accessRoomViewModel.enterTheRoom(roomId) }

        coVerify { enterTheRoom.enter(roomId) }
    }
}