package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.common.flowResource
import com.github.eliascoelho911.youplay.domain.entities.Room
import com.github.eliascoelho911.youplay.domain.usecases.session.GetCurrentRoomId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll

class GetCurrentRoom(
    private val fetchRoomById: FetchRoomById,
    private val getCurrentRoomId: GetCurrentRoomId,
) {
    val currentRoom: (observe: Boolean) -> Flow<Resource<Room>> = { observe ->
        flowResource {
            getCurrentRoomId.get()?.let { emitAll(fetchRoomById.invoke(it, observe)) }
        }
    }
}