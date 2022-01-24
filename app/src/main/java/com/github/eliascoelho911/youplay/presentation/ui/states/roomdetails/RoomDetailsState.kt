package com.github.eliascoelho911.youplay.presentation.ui.states.roomdetails

import androidx.compose.material.BottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.github.eliascoelho911.youplay.presentation.ui.states.visibility.VisibilityState

class RoomDetailsState(
    val bottomDrawerOptionsState: BottomDrawerState,
    val exitFromRoomDialogState: VisibilityState,
    val loadingActionState: VisibilityState,
)

@Composable
fun rememberRoomDetailsState(
    bottomDrawerOptionsState: BottomDrawerState,
    exitFromRoomDialogState: VisibilityState,
    loadingActionState: VisibilityState,
): RoomDetailsState =
    remember(bottomDrawerOptionsState, exitFromRoomDialogState, loadingActionState) {
        RoomDetailsState(bottomDrawerOptionsState, exitFromRoomDialogState, loadingActionState)
    }