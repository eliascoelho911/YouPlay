package com.github.eliascoelho911.youplay.domain.common.room

import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository

class UpdateRoom(
    private val roomRepository: RoomRepository
) {
    suspend fun update(room: Room) {
        roomRepository.updateRoom(room)
    }
}