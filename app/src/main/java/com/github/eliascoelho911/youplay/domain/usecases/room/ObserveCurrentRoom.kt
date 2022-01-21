package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.domain.common.room.ObserveRoomById
import com.github.eliascoelho911.youplay.domain.common.session.GetCurrentRoomId
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.global.catchExceptions
import com.github.eliascoelho911.youplay.global.flowResource
import kotlinx.coroutines.flow.emitAll

class ObserveCurrentRoom(
    private val observeRoomById: ObserveRoomById,
    private val getCurrentRoomId: GetCurrentRoomId,
) {
    fun observe() = flowResource<Room> {
        val currentRoomId = getCurrentRoomId.get()

        assert(currentRoomId != null)
        emitAll(observeRoomById.observe(getCurrentRoomId.get()!!))
    }.catchExceptions()
}