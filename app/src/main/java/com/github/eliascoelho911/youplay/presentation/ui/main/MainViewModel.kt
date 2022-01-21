package com.github.eliascoelho911.youplay.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AuthenticateUserOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom
import com.github.eliascoelho911.youplay.domain.util.spotify.UserIsAuthenticatedOnSpotify
import com.github.eliascoelho911.youplay.domain.util.user.UserIsInSomeRoom

class MainViewModel(
    private val userIsAuthenticatedOnSpotify: UserIsAuthenticatedOnSpotify,
    private val authenticateUserOnSpotify: AuthenticateUserOnSpotify,
    private val userIsInSomeRoom: UserIsInSomeRoom,
    private val enterTheRoom: EnterTheRoom,
) : ViewModel() {
    suspend fun userIsAuthenticatedOnSpotify() = userIsAuthenticatedOnSpotify.get()

    suspend fun userIsInSomeRoom() = userIsInSomeRoom.get()

    suspend fun authenticateUserOnSpotify(code: String) {
        authenticateUserOnSpotify.authenticate(code)
    }

    suspend fun enterTheRoom(id: String) {
        enterTheRoom.enter(id)
    }
}