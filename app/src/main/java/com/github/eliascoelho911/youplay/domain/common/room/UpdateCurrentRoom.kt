package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.entities.Room

class UpdateCurrentRoom(
    private val updateRoom: UpdateRoom,
    private val getCurrentRoom: GetCurrentRoom,
) {
    suspend fun update(roomModification: suspend Room.() -> Room) {
        getCurrentRoom.get().lastResult().onSuccess { currentRoom ->
            val room = currentRoom.roomModification()
            updateRoom.update(room)
        }.onFailure {
            throw it
        }
    }
}