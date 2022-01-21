package com.github.eliascoelho911.youplay.presentation.ui.main

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AuthenticateUserOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom
import com.github.eliascoelho911.youplay.domain.util.spotify.UserIsAuthenticatedOnSpotify
import com.github.eliascoelho911.youplay.domain.util.user.UserIsInSomeRoom
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class MainViewModelTest : BaseTest() {
    @MockK
    private lateinit var userIsAuthenticatedOnSpotify: UserIsAuthenticatedOnSpotify

    @MockK
    private lateinit var authenticateUserOnSpotify: AuthenticateUserOnSpotify

    @MockK
    private lateinit var userIsInSomeRoom: UserIsInSomeRoom

    @MockK
    private lateinit var enterTheRoom: EnterTheRoom

    @InjectMockKs
    private lateinit var mainViewModel: MainViewModel

    @Test
    fun testUserIsInSomeRoom() {
        coEvery { userIsInSomeRoom.get() } returns true

        runBlocking {
            mainViewModel.userIsInSomeRoom()
            assertTrue(mainViewModel.userIsInSomeRoom())
        }
    }

    @Test
    fun testEnterTheRoom() {
        val id = "id"

        coEvery { enterTheRoom.enter(id) } returns Unit

        runBlocking {
            mainViewModel.enterTheRoom(id)
        }

        coVerify { enterTheRoom.enter(id) }
    }
}