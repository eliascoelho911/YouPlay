package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.entities.PlayerData
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser

class CreateNewRoom(
    private val roomRepository: RoomRepository,
    private val getLoggedUser: GetLoggedUser,
) {
    @Throws(NoSuchElementException::class)
    suspend fun invoke(id: String, name: String) {
        getLoggedUser.loggedUser.lastResult().onSuccess { user ->
            roomRepository.add(Room(
                id = id,
                name = name,
                ownerId = user.id,
                currentMusicId = null,
                player = PlayerData()))
        }.onFailure {
            throw NoSuchElementException(it.message)
        }
    }
}