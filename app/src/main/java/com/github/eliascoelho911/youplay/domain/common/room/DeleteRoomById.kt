package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository

class DeleteRoomById(
    private val roomRepository: RoomRepository
) {
    suspend fun delete(id: ID) {
        roomRepository.deleteRoomById(id)
    }
}