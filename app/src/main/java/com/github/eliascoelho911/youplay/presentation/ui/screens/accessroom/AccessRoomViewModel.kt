package com.github.eliascoelho911.youplay.presentation.ui.screens.accessroom

import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom

class AccessRoomViewModel(
    private val enterTheRoom: EnterTheRoom
): ViewModel() {
    suspend fun enterTheRoom(roomId: String) {
        enterTheRoom.enter(roomId)
    }
}