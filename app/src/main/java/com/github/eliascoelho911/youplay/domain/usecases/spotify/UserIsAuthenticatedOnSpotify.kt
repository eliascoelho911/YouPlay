package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.domain.usecases.session.GetAuthSessionId

class UserIsAuthenticatedOnSpotify(private val getAuthSessionId: GetAuthSessionId) {
    suspend fun get() = getAuthSessionId.get() != null
}