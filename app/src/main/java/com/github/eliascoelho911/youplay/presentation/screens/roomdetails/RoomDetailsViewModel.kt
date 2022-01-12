package com.github.eliascoelho911.youplay.presentation.screens.roomdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.UserExitFromRoom
import kotlinx.coroutines.launch

class RoomDetailsViewModel(
    private val userExitFromRoom: UserExitFromRoom,
    observeCurrentRoom: ObserveCurrentRoom,
    observeCurrentMusic: ObserveCurrentMusic,
) : ViewModel() {

    val currentRoom = observeCurrentRoom.observe()
    val currentMusic = observeCurrentMusic.observe()

    fun userExitFromRoom() {
        viewModelScope.launch {
            userExitFromRoom.exit()
        }
    }
}