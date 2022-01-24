package com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails

import androidx.compose.material.BottomDrawerValue.Closed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.ui.base.components.navigate
import com.github.eliascoelho911.youplay.presentation.ui.base.screens.roomdetails.OptionData
import com.github.eliascoelho911.youplay.presentation.ui.base.screens.roomdetails.RoomDetailsScreen
import com.github.eliascoelho911.youplay.presentation.ui.main.MainActivity
import com.github.eliascoelho911.youplay.presentation.ui.states.roomdetails.rememberRoomDetailsState
import com.github.eliascoelho911.youplay.presentation.ui.states.visibility.Visibility.Hide
import com.github.eliascoelho911.youplay.presentation.ui.states.visibility.rememberVisibilityState
import com.github.eliascoelho911.youplay.util.Resource
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

fun MainActivity.roomDetailsScreenImpl(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
) {
    navGraphBuilder.composable(Destination.RoomDetails.baseRoute) {
        val viewModel: RoomDetailsViewModel by viewModel()
        val coroutineScope = rememberCoroutineScope()

        val bottomDrawerOptionsState = rememberBottomDrawerState(initialValue = Closed)
        val exitFromRoomDialogState = rememberVisibilityState(initialValue = Hide)
        val loadingActionState = rememberVisibilityState(initialValue = Hide)
        val state = rememberRoomDetailsState(bottomDrawerOptionsState,
            exitFromRoomDialogState,
            loadingActionState)

        val currentRoomResource by viewModel.currentRoomResource.observeAsState(initial = Resource.loading())
        val currentMusicResource by viewModel.currentMusicResource.observeAsState(initial = Resource.loading())

        RoomDetailsScreen(
            state,
            currentRoomResource,
            currentMusicResource,
            optionsItems = viewModel.optionsItems,
            backgroundColor = Color.Blue,
            onUpdateRoomName = { name ->
                coroutineScope.launch {
                    runCatching {
                        viewModel.updateCurrentRoomName(name)
                    }.onFailure {
                        showError(it)
                    }
                }
            },
            onClickOptions = {
                coroutineScope.launch {
                    bottomDrawerOptionsState.expand()
                }
            },
            onClickExitFromRoom = {
                exitFromRoomDialogState.show()
            },
            onConfirmExitFromRoom = {
                exitFromRoomDialogState.hide()
                loadingActionState.show()
                coroutineScope.launch {
                    runCatching {
                        viewModel.userExitFromRoom()
                    }.onSuccess {
                        navController.navigate(Destination.Home) {
                            popUpTo(0)
                        }
                    }.onFailure {
                        showError(it)
                    }
                }
            },
            onDismissExitFromRoom = { exitFromRoomDialogState.hide() },
            onTimeChange = {},
            onClickShuffleButton = {},
            onClickSkipToPreviousMusicButton = {},
            onClickPlayOrPauseButton = {},
            onClickSkipToNextMusicButton = {},
            onClickRepeatButton = {},
        )
    }
}