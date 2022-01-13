package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.usecases.room.DeleteRoomById
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateRoom

class UserExitFromRoom(
    private val getCurrentRoom: GetCurrentRoom,
    private val getLoggedUser: GetLoggedUser,
    private val deleteRoomById: DeleteRoomById,
    private val updateRoom: UpdateRoom,
) {
    suspend fun exit() {
        getCurrentRoom.get().lastResult().onSuccess { currentRoom ->
            getLoggedUser.get().lastResult().onSuccess { loggedUser ->
                if (currentRoom.ownerId == loggedUser.id) {
                    deleteRoomById.delete(currentRoom.id)
                } else {
                    updateRoom.update(room = currentRoom.copy(
                        users = currentRoom.users.toMutableList().apply { remove(loggedUser.id) }
                    ))
                }
            }.onFailure {
                throw it
            }
        }.onFailure {
            throw it
        }
    }
}