package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.util.Messages
import com.github.eliascoelho911.youplay.domain.common.session.PutAuthSessionId
import com.github.eliascoelho911.youplay.domain.common.spotify.AddSpotifyRefreshToken
import com.github.eliascoelho911.youplay.domain.util.runChangingExceptionMessage

class AuthenticateUserOnSpotify(
    private val addSpotifyRefreshToken: AddSpotifyRefreshToken,
    private val putAuthSessionId: PutAuthSessionId,
    private val errorMessages: Messages.Error,
) {
    suspend fun authenticate(code: String) =
        runChangingExceptionMessage(message = errorMessages.authenticateUserOnSpotify) {
            addSpotifyRefreshToken.add(code).let { id ->
                putAuthSessionId.put(id)
            }
        }
}