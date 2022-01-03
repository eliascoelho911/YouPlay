package com.github.eliascoelho911.youplay.domain.usecases.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession

class GetCurrentRoomId(
    private val applicationSession: ApplicationSession
) {
    suspend fun get() = applicationSession.getCurrentRoomId()
}