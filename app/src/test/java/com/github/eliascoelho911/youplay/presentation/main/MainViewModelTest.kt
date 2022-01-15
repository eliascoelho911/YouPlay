package com.github.eliascoelho911.youplay.presentation.main

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AuthenticateUserOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.spotify.UserIsAuthenticatedOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.user.UserIsInSomeRoom
import io.mockk.coEvery
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
}