package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.entities.copyAddingUsers
import com.github.eliascoelho911.youplay.domain.exceptions.RoomNotFoundException
import com.github.eliascoelho911.youplay.domain.usecases.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.session.PutCurrentRoomId

class EnterTheRoom(
    private val getLoggedUser: GetLoggedUser,
    private val putCurrentRoomId: PutCurrentRoomId,
    private val updateCurrentRoom: UpdateCurrentRoom,
    private val checkIfRoomExistsById: CheckIfRoomExistsById,
) {
    suspend fun enter(roomId: ID) {
        if (checkIfRoomExistsById.check(roomId)) {
            getLoggedUser.get().lastResult().onSuccess { loggedUser ->
                putCurrentRoomId.put(roomId)
                updateCurrentRoom.update {
                    copyAddingUsers(loggedUser.id)
                }
            }.onFailure {
                throw it
            }
        } else {
            throw RoomNotFoundException(roomId)
        }
    }
}