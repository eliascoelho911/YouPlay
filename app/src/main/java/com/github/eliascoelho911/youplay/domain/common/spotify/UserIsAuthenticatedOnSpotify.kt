package com.github.eliascoelho911.youplay.domain.common.spotify

import com.github.eliascoelho911.youplay.domain.common.session.GetAuthSessionId

class UserIsAuthenticatedOnSpotify(private val getAuthSessionId: GetAuthSessionId) {
    suspend fun get() = getAuthSessionId.get() != null
}