package com.github.eliascoelho911.youplay.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AuthenticateUserOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.spotify.UserIsAuthenticatedOnSpotify
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    coroutineContext: CoroutineContext,
    userIsAuthenticatedOnSpotify: UserIsAuthenticatedOnSpotify,
    private val authenticateUserOnSpotify: AuthenticateUserOnSpotify,
) : ViewModel() {
    val userIsAuthenticatedOnSpotify = userIsAuthenticatedOnSpotify.get().asLiveData(
        context = coroutineContext
    )

    fun authenticateUserOnSpotify(code: String) {
        viewModelScope.launch {
            authenticateUserOnSpotify.authenticate(code)
        }
    }
}