package com.github.eliascoelho911.youplay.domain.repositories

import com.github.eliascoelho911.youplay.util.Resource
import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.entities.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun observeRoomById(id: ID): Flow<Resource<Room>>

    fun getRoomById(id: ID): Flow<Resource<Room>>

    suspend fun add(room: Room)

    suspend fun deleteRoomById(id: ID)

    suspend fun updateRoom(room: Room)
}