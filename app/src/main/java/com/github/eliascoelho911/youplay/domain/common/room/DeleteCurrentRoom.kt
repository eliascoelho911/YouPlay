package com.github.eliascoelho911.youplay.domain.common.room

import com.github.eliascoelho911.youplay.global.assertSuccess
import com.github.eliascoelho911.youplay.global.lastResult
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom

class DeleteCurrentRoom(
    private val getCurrentRoom: GetCurrentRoom,
    private val deleteRoomById: DeleteRoomById
) {
    suspend fun delete() {
        getCurrentRoom.get().lastResult().assertSuccess { currentRoom ->
            deleteRoomById.delete(currentRoom.id)
        }
    }
}