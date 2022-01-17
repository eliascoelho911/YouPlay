package com.github.eliascoelho911.youplay.domain.usecases.room

import com.github.eliascoelho911.youplay.common.Resource
import com.github.eliascoelho911.youplay.common.lastResult
import com.github.eliascoelho911.youplay.domain.common.room.GetRoomById
import com.github.eliascoelho911.youplay.domain.entities.ID

//todo isso é realmente uma usecase ou seria uma classe util?
class CheckIfRoomExistsById(
    private val getRoomById: GetRoomById,
) {
    suspend fun check(id: ID): Boolean =
        getRoomById.get(id).lastResult() is Resource.Success
}