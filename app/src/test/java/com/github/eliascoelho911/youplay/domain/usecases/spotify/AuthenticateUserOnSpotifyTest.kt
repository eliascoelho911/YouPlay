package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.domain.usecases.session.PutAuthSessionId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AuthenticateUserOnSpotifyTest {
    @RelaxedMockK
    private lateinit var addSpotifyRefreshToken: AddSpotifyRefreshToken

    @RelaxedMockK
    private lateinit var putAuthSessionId: PutAuthSessionId

    @InjectMockKs
    private lateinit var authenticateUserOnSpotify: AuthenticateUserOnSpotify

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun authenticateUserOnSpotify() {
        val code = "code"
        val id = "id"

        coEvery { addSpotifyRefreshToken.invoke(code) } returns id

        runBlocking { authenticateUserOnSpotify.invoke(code) }

        coVerify { putAuthSessionId.invoke(id) }
    }
}