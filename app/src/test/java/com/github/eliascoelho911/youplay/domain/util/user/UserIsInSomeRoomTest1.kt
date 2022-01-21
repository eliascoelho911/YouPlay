package com.github.eliascoelho911.youplay.domain.util.user

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.common.session.GetCurrentRoomId
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UserIsInSomeRoomTest : BaseTest() {
    @MockK
    private lateinit var getCurrentRoomId: GetCurrentRoomId

    @InjectMockKs
    private lateinit var userIsInSomeRoom: UserIsInSomeRoom

    @Test
    fun testDeveRetornarTrueQuandoIDNaoForNulo() {
        coEvery { getCurrentRoomId.get() } returns "id"

        runBlocking {
            assertTrue(userIsInSomeRoom.get())
        }
    }

    @Test
    fun testDeveRetornarFalseQuandoIDForNulo() {
        coEvery { getCurrentRoomId.get() } returns null

        runBlocking {
            assertFalse(userIsInSomeRoom.get())
        }
    }
}