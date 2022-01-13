package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.lastResult

class DeleteCurrentRoom(
    private val getCurrentRoom: GetCurrentRoom,
    private val deleteRoomById: DeleteRoomById,
) {
    suspend fun delete() {
        getCurrentRoom.get().lastResult().onSuccess { currentRoom ->
            deleteRoomById.delete(currentRoom.id)
        }.onFailure {
            throw it
        }
    }
}