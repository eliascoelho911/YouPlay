package com.github.eliascoelho911.youplay.presentation.screens.home

import android.content.Context
import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.ref.WeakReference

class HomeViewModelTest: BaseTest() {
    @MockK
    private lateinit var createNewRoom: CreateNewRoom

    @MockK
    private lateinit var enterTheRoom: EnterTheRoom

    @MockK
    private lateinit var context: WeakReference<Context>

    @MockK
    private lateinit var getLoggedUser: GetLoggedUser

    @InjectMockKs
    private lateinit var viewModel: HomeViewModel

    @Test
    fun testEnterTheRoom() {
        val roomId = "roomId"
        coEvery { enterTheRoom.enter(roomId) } returns Unit

        runBlocking { viewModel.enterTheRoom(roomId) }

        coVerify { enterTheRoom.enter(roomId) }
    }
}