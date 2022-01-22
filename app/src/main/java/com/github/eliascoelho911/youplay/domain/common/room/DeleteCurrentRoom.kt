package com.github.eliascoelho911.youplay.domain.common.room

import com.github.eliascoelho911.youplay.util.assertSuccess
import com.github.eliascoelho911.youplay.util.lastResult
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