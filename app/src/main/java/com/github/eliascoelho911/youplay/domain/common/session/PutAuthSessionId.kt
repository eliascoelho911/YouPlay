package com.github.eliascoelho911.youplay.domain.common.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession

class PutAuthSessionId(private val applicationSession: ApplicationSession) {
    suspend fun put(id: String) {
        applicationSession.putAuthId(id)
    }
}