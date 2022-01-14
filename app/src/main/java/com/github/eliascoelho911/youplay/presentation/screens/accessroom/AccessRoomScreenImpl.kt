package com.github.eliascoelho911.youplay.presentation.screens.accessroom

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.eliascoelho911.youplay.presentation.main.slideInHorizontallyTransition
import com.github.eliascoelho911.youplay.presentation.main.slideOutHorizontallyTransition
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.util.AnimationDurations
import com.google.accompanist.navigation.animation.composable

fun ComponentActivity.accessRoomScreenImpl(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
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
        AccessRoomScreen(roomAccessIsLoading = false,
            onBackPressed = { navController.popBackStack() },
            onClickAccessWithQrCode = {},
            onClickAccessWithCodeButton = {})
    }
}