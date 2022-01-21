package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.room.UpdateRoom
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.roomMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateCurrentRoomTest : BaseTest() {
    @MockK
    private lateinit var updateRoom: UpdateRoom

    @MockK
    private lateinit var getCurrentRoom: GetCurrentRoom

    @InjectMockKs
    private lateinit var updateCurrentRoom: UpdateCurrentRoom

    @Test
    fun testUpdateCurrentRoom() {
        val room = roomMock

        coEvery { updateRoom.update(any()) } returns Unit
        every { getCurrentRoom.get() } returns flowOf(Resource.success(room))

        val roomModification: suspend Room.() -> Room = { room }
        runBlocking { updateCurrentRoom.update(roomModification) }

        coVerify { updateRoom.update(roomModification(room)) }
    }
}