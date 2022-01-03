package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.flowResource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import kotlinx.coroutines.flow.emitAll

class GetCurrentRoom(
    private val fetchRoomById: FetchRoomById,
    private val applicationSession: ApplicationSession,
) {
    fun get() = flowResource<Room> {
        applicationSession.getCurrentRoomId()?.let { emitAll(fetchRoomById.fetch(it)) }
    }
}