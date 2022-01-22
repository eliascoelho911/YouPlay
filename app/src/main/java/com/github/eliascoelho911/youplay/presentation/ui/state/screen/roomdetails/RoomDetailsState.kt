package com.github.eliascoelho911.youplay.presentation.ui.state.screen.roomdetails

import androidx.compose.runtime.State

interface RoomDetailsState {
    val optionsIsVisible: State<Boolean>
    val exitFromRoomDialogIsVisible: State<Boolean>
    val loadingActionIsVisible: State<Boolean>
}