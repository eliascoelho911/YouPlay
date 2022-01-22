package com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.UserExitFromRoom
import com.github.eliascoelho911.youplay.presentation.ui.state.screen.roomdetails.RoomDetailsData
import com.github.eliascoelho911.youplay.presentation.ui.state.screen.roomdetails.RoomDetailsState

class RoomDetailsViewModel(
    private val userExitFromRoom: UserExitFromRoom,
    private val updateCurrentRoom: UpdateCurrentRoom,
    private val observeCurrentRoom: ObserveCurrentRoom,
    private val observeCurrentMusic: ObserveCurrentMusic,
) : ViewModel(), RoomDetailsState, RoomDetailsData {

    override val currentRoomResource by lazy { observeCurrentRoom.observe().asLiveData() }
    override val currentMusicResource by lazy { observeCurrentMusic.observe().asLiveData() }

    override val optionsIsVisible: MutableState<Boolean> = mutableStateOf(false)
    override val exitFromRoomDialogIsVisible: MutableState<Boolean> = mutableStateOf(false)
    override val loadingActionIsVisible: MutableState<Boolean> = mutableStateOf(false)

    suspend fun userExitFromRoom() {
        userExitFromRoom.exit()
    }

    suspend fun updateCurrentRoomName(name: String) {
        updateCurrentRoom.update { copy(name = name) }
    }
}