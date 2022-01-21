package com.github.eliascoelho911.youplay.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AuthenticateUserOnSpotify
import com.github.eliascoelho911.youplay.domain.util.spotify.UserIsAuthenticatedOnSpotify
import com.github.eliascoelho911.youplay.domain.util.user.UserIsInSomeRoom
import kotlinx.coroutines.launch

class MainViewModel(
    private val userIsAuthenticatedOnSpotify: UserIsAuthenticatedOnSpotify,
    private val authenticateUserOnSpotify: AuthenticateUserOnSpotify,
    private val userIsInSomeRoom: UserIsInSomeRoom,
) : ViewModel() {
    suspend fun userIsAuthenticatedOnSpotify() = userIsAuthenticatedOnSpotify.get()

    suspend fun userIsInSomeRoom() = userIsInSomeRoom.get()

    fun authenticateUserOnSpotify(code: String) {
        viewModelScope.launch {
            authenticateUserOnSpotify.authenticate(code)
        }
    }
}