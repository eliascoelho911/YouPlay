package com.github.eliascoelho911.youplay.presentation.screens.roomdetails

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.eliascoelho911.youplay.presentation.common.navigate
import com.github.eliascoelho911.youplay.presentation.main.MainActivity
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

fun MainActivity.roomDetailsScreenImpl(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
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
                        navController.navigate(Destination.Home) {
                            popUpTo(0)
                        }
                    }.onFailure {
                        showError(it)
                    }
                }
            },
            onClickOptions = {},
            onUpdateRoomName = { name ->
                lifecycleScope.launch {
                    runCatching {
                        viewModel.updateCurrentRoomName(name)
                    }.onFailure {
                        showError(it)
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