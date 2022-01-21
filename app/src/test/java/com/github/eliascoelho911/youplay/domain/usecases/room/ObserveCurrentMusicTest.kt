package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.assertIsResourceFailure
import com.github.eliascoelho911.youplay.assertIsResourceSuccess
import com.github.eliascoelho911.youplay.global.Messages
import com.github.eliascoelho911.youplay.global.Resource
import com.github.eliascoelho911.youplay.global.lastResult
import com.github.eliascoelho911.youplay.musicMock
import com.github.eliascoelho911.youplay.roomMock
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ObserveCurrentMusicTest : BaseTest() {
    @MockK
    private lateinit var observeCurrentRoom: ObserveCurrentRoom

    @InjectMockKs
    private lateinit var observeCurrentMusic: ObserveCurrentMusic

    @Test
    fun testDeveRetornarAMusicaAtualQuandoHouver() {
        val room = roomMock
        val roomFlow = flowOf(Resource.success(room))

        every { observeCurrentRoom.observe() } returns roomFlow

        runBlocking {
            assertIsResourceSuccess(observeCurrentMusic.observe().lastResult(), musicMock)
        }
    }

    @Test
    fun testDeveRetornarFalhaQuandoNaoHouverMusicaAtual() {
        val room = roomMock.copy(currentMusicId = null)
        val roomFlow = flowOf(Resource.success(room))

        every { observeCurrentRoom.observe() } returns roomFlow

        runBlocking {
            assertIsResourceFailure(observeCurrentMusic.observe().lastResult())
        }
    }

    @Test
    fun testDeveRetornarFalhaQuandoNaoEncontrarMusicaAtualNaPlaylist() {
        val room = roomMock.copy(playlist = emptyList())
        val roomFlow = flowOf(Resource.success(room))

        every { observeCurrentRoom.observe() } returns roomFlow

        runBlocking {
            assertIsResourceFailure(observeCurrentMusic.observe().lastResult())
        }
    }
}