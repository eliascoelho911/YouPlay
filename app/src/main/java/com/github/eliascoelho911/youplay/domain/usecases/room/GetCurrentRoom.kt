package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.flowResource
import com.github.eliascoelho911.youplay.domain.common.room.GetRoomById
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.common.session.GetCurrentRoomId
import kotlinx.coroutines.flow.emitAll

class GetCurrentRoom(
    private val getRoomById: GetRoomById,
    private val getCurrentRoomId: GetCurrentRoomId,
) {
    fun get() = flowResource<Room> {
        getCurrentRoomId.get()?.let { id ->
            emitAll(getRoomById.get(id))
        }
    }
}