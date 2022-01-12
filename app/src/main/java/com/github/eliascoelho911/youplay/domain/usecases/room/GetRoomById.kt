package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository

class GetRoomById(
    private val roomRepository: RoomRepository,
) {
    suspend fun get(id: ID) = roomRepository.getRoomById(id)
}