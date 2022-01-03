package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.domain.usecases.session.PutAuthSessionId

class AuthenticateUserOnSpotify(
    private val addSpotifyRefreshToken: AddSpotifyRefreshToken,
    private val putAuthSessionId: PutAuthSessionId,
) {
    suspend fun invoke(code: String) {
        addSpotifyRefreshToken.invoke(code).let { id ->
            putAuthSessionId.invoke(id)
        }
    }
}