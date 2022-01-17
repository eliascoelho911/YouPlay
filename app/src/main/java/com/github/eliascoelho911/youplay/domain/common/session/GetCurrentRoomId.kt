package com.github.eliascoelho911.youplay.domain.common.session

import com.github.eliascoelho911.youplay.domain.session.ApplicationSession

class GetCurrentRoomId(
    private val applicationSession: ApplicationSession
) {
    suspend fun get() = applicationSession.getCurrentRoomId()
}