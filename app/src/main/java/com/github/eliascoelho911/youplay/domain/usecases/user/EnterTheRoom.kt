package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.util.Messages
import com.github.eliascoelho911.youplay.util.assertSuccess
import com.github.eliascoelho911.youplay.util.lastResult
import com.github.eliascoelho911.youplay.domain.util.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.entities.copyAddingUsers
import com.github.eliascoelho911.youplay.domain.exceptions.DomainErrorException
import com.github.eliascoelho911.youplay.domain.util.runChangingExceptionMessage

class EnterTheRoom(
    private val getLoggedUser: GetLoggedUser,
    private val putCurrentRoomId: PutCurrentRoomId,
    private val updateCurrentRoom: UpdateCurrentRoom,
    private val checkIfRoomExistsById: CheckIfRoomExistsById,
    private val errorMessages: Messages.Error,
) {
    suspend fun enter(roomId: ID) {
        if (!checkIfRoomExistsById.check(roomId))
            throw DomainErrorException(errorMessages.roomNotFound)

        runChangingExceptionMessage(errorMessages.enterTheRoom) {
            getLoggedUser.get().lastResult().assertSuccess { loggedUser ->
                putCurrentRoomId.put(roomId)
                updateCurrentRoom.update {
                    copyAddingUsers(loggedUser.id)
                }
            }
        }
    }
}