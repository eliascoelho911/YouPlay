package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.entities.ID

class CheckIfRoomExistsById(
    private val getRoomById: GetRoomById,
) {
    suspend fun check(id: ID): Boolean =
        getRoomById.get(id).lastResult() is Resource.Success
}