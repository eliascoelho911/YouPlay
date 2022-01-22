package com.github.eliascoelho911.youplay.presentation.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.eliascoelho911.youplay.presentation.ui.base.components.navigate
import com.github.eliascoelho911.youplay.presentation.ui.main.MainActivity
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.util.RoomIDGenerator
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

fun MainActivity.homeScreenImpl(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
) {
    navGraphBuilder.composable(Destination.Home.baseRoute) {
        val viewModel: HomeViewModel by viewModel()
        var createRoomButtonIsLoading by remember { mutableStateOf(false) }

        HomeScreen(viewModel = viewModel,
            onClickToCreateRoom = {
                createRoomButtonIsLoading = true
                val roomId = RoomIDGenerator.generate()
                lifecycleScope.launch {
                    runCatching {
                        viewModel.createNewRoom(roomId)
                        viewModel.enterTheRoom(roomId)
                    }.onSuccess {
                        navController.navigate(Destination.RoomDetails)
                    }.onFailure {
                        showError(it)
                        createRoomButtonIsLoading = false
                    }
                }
            },
            onClickToEnterRoom = { navController.navigate(Destination.AccessRoom) },
            createRoomButtonIsLoading = createRoomButtonIsLoading)
    }
}