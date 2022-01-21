package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.assertIsResourceSuccess
import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.domain.common.room.GetRoomById
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetRoomByIdTest: BaseTest() {
    @MockK
    private lateinit var roomRepository: RoomRepository

    @InjectMockKs
    private lateinit var getRoomById: GetRoomById

    @Test
    fun testGetRoomById() {
        val id = "id"
        val room: Room = mockk()

        every { roomRepository.getRoomById(id) } returns flowOf(Resource.success(room))

        runBlocking {
            getRoomById.get(id).collect {
                assertIsResourceSuccess(it, room)
            }
        }
    }
}