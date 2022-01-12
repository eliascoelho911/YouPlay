package com.github.eliascoelho911.youplay.domain.usecases.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession

class PutCurrentRoomId(
    private val applicationSession: ApplicationSession
) {
    suspend fun put(id: String?) {
        applicationSession.putCurrentRoomId(id)
    }
}