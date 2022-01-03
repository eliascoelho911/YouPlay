package com.github.eliascoelho911.youplay.domain.usecases.spotify

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.eliascoelho911.youplay.common.collectResource
import com.github.eliascoelho911.youplay.domain.usecases.session.GetAuthSessionId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserIsAuthenticatedOnSpotifyTest {
    @RelaxedMockK
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
    fun userMustBeAuthenticatedWhenSessionIdIsNotNull() {
        val id = "id"

        coEvery { getAuthSessionId.get() } returns id

        runBlocking {
            userIsAuthenticatedOnSpotify.isAuthenticated.collectResource {
                onSuccess {
                    assertTrue(it)
                }
            }
        }
    }

    @Test
    fun userMustNotBeAuthenticatedWhenSessionIdIsNull() {
        val id = null

        coEvery { getAuthSessionId.get() } returns id

        runBlocking {
            userIsAuthenticatedOnSpotify.isAuthenticated.collectResource {
                onSuccess {
                    assertFalse(it)
                }
            }
        }
    }
}