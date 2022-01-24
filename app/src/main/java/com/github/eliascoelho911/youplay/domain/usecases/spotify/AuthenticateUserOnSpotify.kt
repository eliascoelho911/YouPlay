package com.github.eliascoelho911.youplay.domain.usecases.spotify

import android.content.Context
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.domain.common.session.PutAuthSessionId
import com.github.eliascoelho911.youplay.domain.common.spotify.AddSpotifyRefreshToken
import com.github.eliascoelho911.youplay.domain.util.runChangingExceptionMessage

class AuthenticateUserOnSpotify(
    private val addSpotifyRefreshToken: AddSpotifyRefreshToken,
    private val putAuthSessionId: PutAuthSessionId,
    private val context: Context
) {
    suspend fun authenticate(code: String) =
        runChangingExceptionMessage(message = context.getString(R.string.error_authenticateUserOnSpotify)) {
            addSpotifyRefreshToken.add(code).let { id ->
                putAuthSessionId.put(id)
            }
        }
}