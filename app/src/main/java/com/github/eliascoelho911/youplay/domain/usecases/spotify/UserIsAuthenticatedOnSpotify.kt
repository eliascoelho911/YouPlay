package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.common.emitErrors
import com.github.eliascoelho911.youplay.common.emitSuccess
import com.github.eliascoelho911.youplay.common.flowResource
import com.github.eliascoelho911.youplay.domain.usecases.session.GetAuthSessionId

class UserIsAuthenticatedOnSpotify(private val getAuthSessionId: GetAuthSessionId) {
    fun get() = flowResource<Boolean> {
        emitSuccess(getAuthSessionId.get() != null)
    }.emitErrors()
}