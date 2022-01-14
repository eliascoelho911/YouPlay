package com.github.eliascoelho911.youplay.presentation.screens.accessroom

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.domain.exceptions.RoomNotFoundException
import com.github.eliascoelho911.youplay.presentation.main.slideInHorizontallyTransition
import com.github.eliascoelho911.youplay.presentation.main.slideOutHorizontallyTransition
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.util.AnimationDurations
import com.github.eliascoelho911.youplay.presentation.util.navigate
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

fun ComponentActivity.accessRoomScreenImpl(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    showError: (error: String) -> Unit
) {
    navGraphBuilder.composable(Destination.AccessRoom.baseRoute, enterTransition = { _, _ ->
        slideInHorizontallyTransition()
    }, exitTransition = { _, target ->
        when (target.destination.route) {
            Destination.CreateRoom.baseRoute -> slideOutHorizontallyTransition()
            else -> fadeOut(animationSpec = tween(AnimationDurations.medium))
        }
    }, popEnterTransition = { _, _ ->
        slideInHorizontallyTransition()
    }, popExitTransition = { _, target ->
        when (target.destination.route) {
            Destination.CreateRoom.baseRoute -> slideOutHorizontallyTransition()
            else -> fadeOut(animationSpec = tween(AnimationDurations.medium))
        }
    }) {
        val viewModel: AccessRoomViewModel by viewModel()

        AccessRoomScreen(roomAccessIsLoading = false,
            onBackPressed = { navController.popBackStack() },
            onClickAccessWithQrCode = {},
            onClickAccessWithCodeButton = { roomId ->
                lifecycleScope.launch {
                    runCatching {
                        viewModel.enterTheRoom(roomId)
                    }.onSuccess {
                        navController.navigate(Destination.RoomDetails)
                    }.onFailure { exception ->
                        if (exception is RoomNotFoundException) {
                            showError(getString(R.string.accessRoom_roomNotFound))
                        } else {
                            showError(getString(R.string.accessRoom_errorOnAccessTheRoom))
                        }
                    }
                }
            })
    }
}