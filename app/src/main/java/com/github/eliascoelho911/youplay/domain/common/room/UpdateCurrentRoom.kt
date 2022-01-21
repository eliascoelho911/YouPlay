package com.github.eliascoelho911.youplay.domain.common.room

import com.github.eliascoelho911.youplay.global.assertSuccess
import com.github.eliascoelho911.youplay.global.lastResult
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom

class UpdateCurrentRoom(
    private val updateRoom: UpdateRoom,
    private val getCurrentRoom: GetCurrentRoom,
) {
    suspend fun update(roomModification: suspend Room.() -> Room) {
        getCurrentRoom.get().lastResult().assertSuccess { currentRoom ->
            val room = currentRoom.roomModification()
            updateRoom.update(room)
        }
    }
}