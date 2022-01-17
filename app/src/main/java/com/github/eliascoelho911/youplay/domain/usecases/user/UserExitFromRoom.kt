package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.entities.copyRemovingUsers
import com.github.eliascoelho911.youplay.domain.usecases.room.DeleteCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.session.PutCurrentRoomId

class UserExitFromRoom(
    private val getLoggedUser: GetLoggedUser,
    private val putCurrentRoomId: PutCurrentRoomId,
    private val getCurrentRoom: GetCurrentRoom,
    private val deleteCurrentRoom: DeleteCurrentRoom,
    private val updateCurrentRoom: UpdateCurrentRoom,
) {
    suspend fun exit() {
        getCurrentRoom.get().lastResult().onSuccess { currentRoom ->
            getLoggedUser.get().lastResult().onSuccess { loggedUser ->
                if (currentRoom.ownerId == loggedUser.id) {
                    deleteCurrentRoom.delete()
                } else {
                    updateCurrentRoom.update {
                        copyRemovingUsers(loggedUser.id)
                    }
                }
                putCurrentRoomId.put(null)
            }.onFailure {
                throw it
            }
        }.onFailure {
            throw it
        }
    }
}