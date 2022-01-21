package com.github.eliascoelho911.youplay.domain.common.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.common.room.UpdateRoom
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateRoomTest : BaseTest() {
    @MockK
    private lateinit var roomRepository: RoomRepository

    @InjectMockKs
    private lateinit var updateRoom: UpdateRoom

    @Test
    fun testUpdateRoom() {
        val room = mockk<Room>()

        coEvery { roomRepository.updateRoom(room) } returns Unit

        runBlocking {
            updateRoom.update(room)
        }

        coVerify { roomRepository.updateRoom(room) }
    }
}