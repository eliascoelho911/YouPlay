package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchRoomByIdTest {
    @MockK
    private lateinit var roomRepository: RoomRepository

    @InjectMockKs
    private lateinit var fetchRoomById: FetchRoomById

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun fetchRoomById() {
        val id = "id"
        val room: Room = mockk()

        every { roomRepository.fetchRoomById(id) } returns flowOf(Resource.success(room))

        runBlocking {
            fetchRoomById.fetch(id).collect {
                assertThat(it, instanceOf(Resource.Success::class.java))

                it.onSuccess { result ->
                    assertEquals(room, result)
                }
            }
        }
    }
}