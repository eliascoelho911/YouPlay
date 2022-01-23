package com.github.eliascoelho911.youplay.presentation.ui.states.roomdetails

import androidx.compose.material.BottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.github.eliascoelho911.youplay.presentation.ui.states.visibility.VisibilityState

class RoomDetailsState(
    val bottomSheetOptionsState: BottomSheetState,
    val exitFromRoomDialogState: VisibilityState,
    val loadingActionState: VisibilityState,
)

@Composable
fun rememberRoomDetailsState(
    bottomSheetOptionsState: BottomSheetState,
    exitFromRoomDialogState: VisibilityState,
    loadingActionState: VisibilityState,
): RoomDetailsState =
    remember(bottomSheetOptionsState, exitFromRoomDialogState, loadingActionState) {
        RoomDetailsState(bottomSheetOptionsState, exitFromRoomDialogState, loadingActionState)
    }