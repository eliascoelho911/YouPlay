package com.github.eliascoelho911.youplay.domain.common.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.assertIsResourceFailure
import com.github.eliascoelho911.youplay.assertIsResourceSuccess
import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ObserveRoomByIdTest: BaseTest() {
    @MockK
    private lateinit var roomRepository: RoomRepository

    @InjectMockKs
    private lateinit var observeRoomById: ObserveRoomById

    @Test
    fun testObserveRoomById() {
        val id = "id"
        val room: Room = mockk()
        val throwable = Throwable()

        every { roomRepository.observeRoomById(id) } returns flowOf(
            Resource.success(room),
            Resource.failure(throwable)
        )

        runBlocking {
            val result = observeRoomById.observe(id).toList()
            assertIsResourceSuccess(result[0], room)
            assertIsResourceFailure(result[1], Throwable::class.java)
        }
    }
}