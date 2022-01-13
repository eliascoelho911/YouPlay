package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteRoomByIdTest : BaseTest() {
    @MockK
    private lateinit var roomRepository: RoomRepository

    @InjectMockKs
    private lateinit var deleteRoomById: DeleteRoomById

    @Test
    fun testDeleteRoomById() {
        val id = "id"

        coEvery { roomRepository.deleteRoomById(id) } returns Unit

        runBlocking {
            deleteRoomById.delete(id)
        }

        coVerify { roomRepository.deleteRoomById(id) }
    }
}