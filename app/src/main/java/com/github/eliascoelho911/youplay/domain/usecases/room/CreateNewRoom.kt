package com.github.eliascoelho911.youplay.domain.usecases.room

import android.content.Context
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.domain.entities.PlayerData
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.domain.util.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.domain.util.runChangingExceptionMessage
import com.github.eliascoelho911.youplay.util.assertSuccess
import com.github.eliascoelho911.youplay.util.lastResult

class CreateNewRoom(
    private val roomRepository: RoomRepository,
    private val getLoggedUser: GetLoggedUser,
    private val checkIfRoomExistsById: CheckIfRoomExistsById,
    private val context: Context,
) {
    suspend fun create(id: String, name: String) =
        runChangingExceptionMessage(message = context.getString(R.string.error_createNewRoom)) {
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