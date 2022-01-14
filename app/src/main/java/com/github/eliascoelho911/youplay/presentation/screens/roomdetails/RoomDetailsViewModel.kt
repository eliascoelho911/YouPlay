package com.github.eliascoelho911.youplay.presentation.screens.roomdetails

import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.UserExitFromRoom

class RoomDetailsViewModel(
    private val userExitFromRoom: UserExitFromRoom,
    private val observeCurrentRoom: ObserveCurrentRoom,
    private val observeCurrentMusic: ObserveCurrentMusic,
) : ViewModel() {

    val currentRoom by lazy { observeCurrentRoom.observe() }
    val currentMusic by lazy { observeCurrentMusic.observe() }

    fun userExitFromRoom() {
        viewModelScope.launch {
            userExitFromRoom.exit()
        }
    }
}