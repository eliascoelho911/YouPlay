package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.Messages
import com.github.eliascoelho911.youplay.common.assertSuccess
import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.entities.PlayerData
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.exceptions.UseCaseErrorException
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser

//todo testar
class CreateNewRoom(
    private val roomRepository: RoomRepository,
    private val getLoggedUser: GetLoggedUser,
    private val checkIfRoomExistsById: CheckIfRoomExistsById,
    private val errorMessages: Messages.Error,
) {
    suspend fun create(id: String, name: String) {
        assert(!checkIfRoomExistsById.check(id)) { errorMessages.createNewRoom }

        getLoggedUser.get().lastResult().assertSuccess(whenSuccess = { user ->
            runCatching {
                roomRepository.add(Room(
                    id = id,
                    name = name,
                    ownerId = user.id,
                    currentMusicId = null,
                    player = PlayerData()))
            }.onFailure {
                throw UseCaseErrorException(errorMessages.createNewRoom)
            }
        }, message = errorMessages.createNewRoom)
    }
}