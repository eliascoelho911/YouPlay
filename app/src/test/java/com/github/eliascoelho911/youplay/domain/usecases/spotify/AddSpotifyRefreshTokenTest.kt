package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.repositories.SpotifyAuthorizationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddSpotifyRefreshTokenTest: BaseTest() {
    @MockK
    private lateinit var spotifyAuthorizationRepository: SpotifyAuthorizationRepository

    @InjectMockKs
    private lateinit var addSpotifyRefreshToken: AddSpotifyRefreshToken

    @Test
    fun testAddSpotifyRefreshToken() {
        val code = "code"
        val id = "id"

        coEvery { spotifyAuthorizationRepository.addRefreshToken(code) } returns id

        runBlocking {
            addSpotifyRefreshToken.add(code)
        }

        coVerify { spotifyAuthorizationRepository.addRefreshToken(code) }
    }
}