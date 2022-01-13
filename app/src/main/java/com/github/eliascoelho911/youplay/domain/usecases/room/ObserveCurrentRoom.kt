package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.emitErrors
import com.github.eliascoelho911.youplay.common.flowResource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.usecases.session.GetCurrentRoomId
import kotlinx.coroutines.flow.emitAll

class ObserveCurrentRoom(
    private val observeRoomById: ObserveRoomById,
    private val getCurrentRoomId: GetCurrentRoomId,
) {
    fun observe() = flowResource<Room> {
        emitAll(observeRoomById.observe(getCurrentRoomId.get()!!))
    }.emitErrors()
}