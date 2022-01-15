package com.github.eliascoelho911.youplay.domain.usecases.user

import com.github.eliascoelho911.youplay.domain.usecases.session.GetCurrentRoomId

class UserIsInSomeRoom(
    private val getCurrentRoomId: GetCurrentRoomId,
) {
    suspend fun get() = getCurrentRoomId.get() != null
}