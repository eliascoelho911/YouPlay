package com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.eliascoelho911.youplay.presentation.ui.base.components.navigate
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.ui.base.screens.RoomDetailsScreen
import com.github.eliascoelho911.youplay.presentation.ui.main.MainActivity
import com.github.eliascoelho911.youplay.presentation.util.setValueToOpposite
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

fun MainActivity.roomDetailsScreenImpl(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
) {
    navGraphBuilder.composable(Destination.RoomDetails.baseRoute) {
        val viewModel: RoomDetailsViewModel by viewModel()

        RoomDetailsScreen(state = viewModel,
            data = viewModel,
            backgroundColor = Color.Blue,
            onClickOptions = { viewModel.optionsIsVisible.setValueToOpposite() },
            onUpdateRoomName = { name ->
                lifecycleScope.launch {
                    runCatching {
                        viewModel.updateCurrentRoomName(name)
                    }.onFailure {
                        showError(it)
                    }
                }
            },
            onClickSkipToPreviousMusicButton = {},
            onClickSkipToNextMusicButton = {},
            onClickPlayOrPauseButton = {},
            onClickShuffleButton = {},
            onClickRepeatButton = {},
            onTimeChange = {},
            onClickExitFromRoom = { viewModel.exitFromRoomDialogIsVisible.value = true },
            onConfirmExitFromRoom = {
                viewModel.exitFromRoomDialogIsVisible.value = false
                viewModel.loadingActionIsVisible.value = true
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
            onDismissExitFromRoom = { viewModel.exitFromRoomDialogIsVisible.value = false }
        )
    }
}