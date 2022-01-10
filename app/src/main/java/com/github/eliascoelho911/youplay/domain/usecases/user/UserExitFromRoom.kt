package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.usecases.room.DeleteRoomById
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateRoom

class UserExitFromRoom(
    private val getCurrentRoom: GetCurrentRoom,
    private val getLoggedUser: GetLoggedUser,
    private val deleteRoomById: DeleteRoomById,
    private val updateRoom: UpdateRoom,
) {
    @Throws(NoSuchElementException::class)
    suspend fun invoke() {
        getCurrentRoom.currentRoom(false).lastResult().onSuccess { currentRoom ->
            getLoggedUser.loggedUser.lastResult().onSuccess { loggedUser ->
                if (currentRoom.ownerId == loggedUser.id) deleteRoomById.invoke(currentRoom.id)
                updateRoom.invoke(room = currentRoom.copy(
                    users = currentRoom.users.toMutableList().apply { remove(loggedUser.id) }
                ))
            }.onFailure {
                throw it
            }
        }.onFailure {
            throw it
        }
    }
}