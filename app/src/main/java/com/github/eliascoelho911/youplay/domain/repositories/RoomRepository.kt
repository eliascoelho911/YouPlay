package com.github.eliascoelho911.youplay.domain.repositories

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.domain.entities.ID
import com.github.eliascoelho911.youplay.domain.entities.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun fetchRoomById(id: ID, observe: Boolean): Flow<Resource<Room>>

    suspend fun add(room: Room)

    suspend fun deleteRoomById(id: ID)

    suspend fun updateRoom(room: Room)
}