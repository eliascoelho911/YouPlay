package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.domain.util.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.domain.common.room.GetRoomById
import com.github.eliascoelho911.youplay.domain.entities.Room
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CheckIfRoomExistsByIdTest : BaseTest() {
    @MockK
    private lateinit var getRoomById: GetRoomById

    @InjectMockKs
    private lateinit var checkIfRoomExistsById: CheckIfRoomExistsById

    @Test
    fun testDeveRetornarTrueQuandoSalaForEncontradaComSucesso() {
        val roomId = "roomId"
        val room = mockk<Room>()
        every { getRoomById.get(roomId) } returns flowOf(Resource.success(room))

        runBlocking {
            val result = checkIfRoomExistsById.check(roomId)
            assertTrue(result)
        }
    }

    @Test
    fun testDeveRetornarFalseQuandoSalaNaoForEncontrada() {
        val roomId = "roomId"
        every { getRoomById.get(roomId) } returns flowOf(Resource.failure(Throwable()))

        runBlocking {
            val result = checkIfRoomExistsById.check(roomId)
            assertFalse(result)
        }
    }
}