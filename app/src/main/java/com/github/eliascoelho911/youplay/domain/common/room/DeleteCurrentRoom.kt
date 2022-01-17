package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.assertSuccess
import com.github.eliascoelho911.youplay.common.lastResult

//todo isso não é uma usecase, mas sim um common
class DeleteCurrentRoom(
    private val getCurrentRoom: GetCurrentRoom,
    private val deleteRoomById: DeleteRoomById
) {
    suspend fun delete() {
        getCurrentRoom.get().lastResult().assertSuccess(whenSuccess = { currentRoom ->
            deleteRoomById.delete(currentRoom.id)
        }, message = errorMessages.createNewRoom)
    }
}