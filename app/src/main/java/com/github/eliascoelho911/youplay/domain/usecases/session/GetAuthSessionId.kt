package com.github.eliascoelho911.youplay.domain.usecases.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession

class GetAuthSessionId(private val applicationSession: ApplicationSession) {
    suspend fun get() = applicationSession.getAuthId()
}