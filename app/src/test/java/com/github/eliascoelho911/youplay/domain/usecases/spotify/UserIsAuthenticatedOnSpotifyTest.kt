package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.BaseTest
import com.github.eliascoelho911.youplay.domain.common.session.GetAuthSessionId
import com.github.eliascoelho911.youplay.domain.util.spotify.UserIsAuthenticatedOnSpotify
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UserIsAuthenticatedOnSpotifyTest: BaseTest() {
    @MockK
    private lateinit var getAuthSessionId: GetAuthSessionId

    @InjectMockKs
    private lateinit var userIsAuthenticatedOnSpotify: UserIsAuthenticatedOnSpotify

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