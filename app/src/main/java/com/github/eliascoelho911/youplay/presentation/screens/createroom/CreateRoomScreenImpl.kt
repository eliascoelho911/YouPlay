package com.github.eliascoelho911.youplay.presentation.screens.createroom

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.util.RoomIDGenerator
import com.github.eliascoelho911.youplay.presentation.util.navigate
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

fun ComponentActivity.createRoomScreenImpl(
    navGraphBuilder: NavGraphBuilder,
    navController: NavHostController,
    showError: (error: String) -> Unit,
) {
    navGraphBuilder.composable(Destination.CreateRoom.baseRoute) {
        val viewModel: CreateRoomViewModel by viewModel()
        var createRoomButtonIsLoading by remember { mutableStateOf(false) }

        CreateRoomScreen(viewModel = viewModel,
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
                        showError(getString(R.string.error_create_new_room))
                        createRoomButtonIsLoading = false
                    }
                }
            },
            onClickToEnterRoom = { navController.navigate(Destination.AccessRoom) },
            createRoomButtonIsLoading = createRoomButtonIsLoading)
    }
}