package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.domain.usecases.session.PutAuthSessionId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AuthenticateUserOnSpotifyTest {
    @MockK
    private lateinit var addSpotifyRefreshToken: AddSpotifyRefreshToken

    @MockK
    private lateinit var putAuthSessionId: PutAuthSessionId

    @InjectMockKs
    private lateinit var authenticateUserOnSpotify: AuthenticateUserOnSpotify

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testAuthenticateUserOnSpotify() {
        val code = "code"
        val id = "id"

        coEvery { addSpotifyRefreshToken.add(code) } returns id
        coEvery { putAuthSessionId.put(id) } returns Unit

        runBlocking { authenticateUserOnSpotify.authenticate(code) }

        coVerify { putAuthSessionId.put(id) }
    }
}