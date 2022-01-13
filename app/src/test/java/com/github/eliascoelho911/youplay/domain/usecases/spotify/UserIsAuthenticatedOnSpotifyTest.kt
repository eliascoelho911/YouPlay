package com.github.eliascoelho911.youplay.domain.usecases.spotify

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.eliascoelho911.youplay.domain.usecases.session.GetAuthSessionId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserIsAuthenticatedOnSpotifyTest {
    @MockK
    private lateinit var getAuthSessionId: GetAuthSessionId

    @InjectMockKs
    private lateinit var userIsAuthenticatedOnSpotify: UserIsAuthenticatedOnSpotify

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testUsuarioDeveEstarAutenticadoQuandoEncontrarOIdDaSessao() {
        val id = "id"

        coEvery { getAuthSessionId.get() } returns id

        runBlocking {
            assertTrue(userIsAuthenticatedOnSpotify.get())
        }
    }

    @Test
    fun testUsuarioNaoDeveEstarAutenticadoQuandoNaoEncontrarOIdDaSessao() {
        coEvery { getAuthSessionId.get() } returns null

        runBlocking {
            assertFalse(userIsAuthenticatedOnSpotify.get())
        }
    }
}