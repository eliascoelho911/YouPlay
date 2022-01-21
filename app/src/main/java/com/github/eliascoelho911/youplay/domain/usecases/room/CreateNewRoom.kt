package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.global.Messages
import com.github.eliascoelho911.youplay.global.assertSuccess
import com.github.eliascoelho911.youplay.global.lastResult
import com.github.eliascoelho911.youplay.domain.util.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.domain.entities.PlayerData
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.domain.util.runChangingExceptionMessage

//todo testar
class CreateNewRoom(
    private val roomRepository: RoomRepository,
    private val getLoggedUser: GetLoggedUser,
    private val checkIfRoomExistsById: CheckIfRoomExistsById,
    private val errorMessages: Messages.Error,
) {
    suspend fun create(id: String, name: String) =
        runChangingExceptionMessage(message = errorMessages.createNewRoom) {
            assert(!checkIfRoomExistsById.check(id))

            getLoggedUser.get().lastResult().assertSuccess { user ->
                roomRepository.add(Room(
                    id = id,
                    name = name,
                    ownerId = user.id,
                    currentMusicId = null,
                    player = PlayerData()))
            }
        }
}