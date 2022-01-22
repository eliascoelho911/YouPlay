package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.assertIsResourceFailure
import com.github.eliascoelho911.youplay.assertIsResourceSuccess
import com.github.eliascoelho911.youplay.domain.common.room.GetRoomById
import com.github.eliascoelho911.youplay.domain.common.session.GetCurrentRoomId
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.util.lastResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetCurrentRoomTest : BaseTest() {
    @MockK
    private lateinit var getCurrentRoomId: GetCurrentRoomId

    @MockK
    private lateinit var getRoomById: GetRoomById

    @InjectMockKs
    private lateinit var getCurrentRoom: GetCurrentRoom

    @Test
    fun testDeveBuscarSalaQuandoUsuarioEstiverEmAlgumaSala() {
        val id = "id"
        val room = mockk<Room>()
        val flow = flowOf(Resource.success(room))

        coEvery { getCurrentRoomId.get() } returns id
        every { getRoomById.get(id) } returns flow

        runBlocking {
            assertIsResourceSuccess(getCurrentRoom.get().lastResult(), room)
        }
    }

    @Test
    fun testDeveLancarErroQuandoUsuarioNaoEstiverEmAlgumaSala() {
        coEvery { getCurrentRoomId.get() } returns null

        runBlocking {
            assertIsResourceFailure(getCurrentRoom.get().lastResult(), AssertionError::class.java)
        }
    }
}