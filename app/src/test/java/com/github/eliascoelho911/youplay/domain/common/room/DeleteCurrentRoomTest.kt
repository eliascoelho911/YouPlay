package com.github.eliascoelho911.youplay.domain.common.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.roomMock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteCurrentRoomTest : BaseTest() {
    @MockK
    private lateinit var getCurrentRoom: GetCurrentRoom

    @MockK
    private lateinit var deleteRoomById: DeleteRoomById

    @InjectMockKs
    private lateinit var deleteCurrentRoom: DeleteCurrentRoom

    @Test
    fun testDeleteCurrentRoom() {
        val room = roomMock

        every { getCurrentRoom.get() } returns flowOf(Resource.success(room))
        coEvery { deleteRoomById.delete(room.id) } returns Unit

        runBlocking { deleteCurrentRoom.delete() }

        coVerify { deleteRoomById.delete(room.id) }
    }
}