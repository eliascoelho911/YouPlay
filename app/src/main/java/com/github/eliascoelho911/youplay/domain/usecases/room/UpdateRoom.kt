package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository

class UpdateRoom(
    private val roomRepository: RoomRepository
) {
    suspend fun invoke(room: Room) {
        roomRepository.updateRoom(room)
    }
}