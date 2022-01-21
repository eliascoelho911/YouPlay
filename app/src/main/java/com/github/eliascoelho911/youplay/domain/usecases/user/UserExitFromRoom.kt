package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.global.Messages
import com.github.eliascoelho911.youplay.global.assertSuccess
import com.github.eliascoelho911.youplay.global.lastResult
import com.github.eliascoelho911.youplay.domain.util.runChangingExceptionMessage
import com.github.eliascoelho911.youplay.domain.common.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.entities.copyRemovingUsers
import com.github.eliascoelho911.youplay.domain.common.room.DeleteCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom

class UserExitFromRoom(
    private val getLoggedUser: GetLoggedUser,
    private val putCurrentRoomId: PutCurrentRoomId,
    private val getCurrentRoom: GetCurrentRoom,
    private val deleteCurrentRoom: DeleteCurrentRoom,
    private val updateCurrentRoom: UpdateCurrentRoom,
    private val errorMessages: Messages.Error,
) {
    suspend fun exit() = runChangingExceptionMessage(errorMessages.userExitFromRoom) {
        getCurrentRoom.get().lastResult().assertSuccess { currentRoom ->
            getLoggedUser.get().lastResult().assertSuccess { loggedUser ->
                if (currentRoom.ownerId == loggedUser.id) {
                    deleteCurrentRoom.delete()
                } else {
                    updateCurrentRoom.update {
                        copyRemovingUsers(loggedUser.id)
                    }
                }
                putCurrentRoomId.put(null)
            }
        }
    }
}