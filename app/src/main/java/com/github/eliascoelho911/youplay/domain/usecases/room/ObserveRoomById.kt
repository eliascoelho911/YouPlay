package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository

class ObserveRoomById(private val repository: RoomRepository) {
    fun observe(id: ID) = repository.observeRoomById(id)
}