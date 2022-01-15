package com.github.eliascoelho911.youplay.presentation.screens.roomdetails

import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.common.navigate
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

fun ComponentActivity.roomDetailsScreenImpl(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    showError: (error: String) -> Unit,
) {
    navGraphBuilder.composable(Destination.RoomDetails.baseRoute) {
        val viewModel: RoomDetailsViewModel by viewModel()

        RoomDetailsScreen(viewModel = viewModel,
            backgroundColor = Color.Blue,
            onConfirmExitFromRoom = {
                lifecycleScope.launch {
                    runCatching {
                        viewModel.userExitFromRoom()
                    }.onSuccess {
                        navController.navigate(Destination.CreateRoom) {
                            popUpTo(0)
                        }
                    }.onFailure {
                        showError(getString(R.string.error_exit_room))
                    }
                }
            },
            onClickOptions = {},
            onUpdateRoomName = { name ->
                lifecycleScope.launch {
                    runCatching {
                        viewModel.updateCurrentRoomName(name)
                    }.onFailure {
                        showError(getString(R.string.errorUpdateRoomName))
                    }
                }
            },
            onClickSkipToNextMusicButton = {},
            onClickPlayOrPauseButton = {},
            onClickSkipToPreviousMusicButton = {},
            onClickShuffleButton = {},
            onClickRepeatButton = {},
            onTimeChange = {})
    }
}