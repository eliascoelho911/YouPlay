package com.github.eliascoelho911.youplay.domain.common.user

import com.github.eliascoelho911.youplay.domain.common.session.GetCurrentRoomId

class UserIsInSomeRoom(
    private val getCurrentRoomId: GetCurrentRoomId,
) {
    suspend fun get() = getCurrentRoomId.get() != null
}