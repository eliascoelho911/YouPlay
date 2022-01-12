package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.domain.usecases.session.PutAuthSessionId

class AuthenticateUserOnSpotify(
    private val addSpotifyRefreshToken: AddSpotifyRefreshToken,
    private val putAuthSessionId: PutAuthSessionId,
) {
    suspend fun authenticate(code: String) {
        addSpotifyRefreshToken.add(code).let { id ->
            putAuthSessionId.put(id)
        }
    }
}