package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.domain.repositories.SpotifyAuthorizationRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddSpotifyRefreshTokenTest {
    @RelaxedMockK
    private lateinit var spotifyAuthorizationRepository: SpotifyAuthorizationRepository

    @InjectMockKs
    private lateinit var addSpotifyRefreshToken: AddSpotifyRefreshToken

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun addSpotifyRefreshToken() {
        val code = "code"

        runBlocking {
            addSpotifyRefreshToken.invoke(code)
        }

        coVerify { spotifyAuthorizationRepository.addRefreshToken(code) }
    }
}