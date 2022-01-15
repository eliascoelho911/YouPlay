package com.github.eliascoelho911.youplay.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.screens.accessroom.accessRoomScreenImpl
import com.github.eliascoelho911.youplay.presentation.screens.createroom.createRoomScreenImpl
import com.github.eliascoelho911.youplay.presentation.screens.roomdetails.roomDetailsScreenImpl
import com.github.eliascoelho911.youplay.presentation.theme.YouPlayTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val spotifyAuthorizationRequest = SpotifyAuthorizationRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showContent()
        ensureUserAuthentication()
    }

    private fun ensureUserAuthentication() {
        lifecycleScope.launch {
            if (!viewModel.userIsAuthenticatedOnSpotify()) {
                requestUserAuthentication()
            }
        }
    }

    private fun showContent() {
        setContent {
            YouPlayTheme {
                val navController = rememberAnimatedNavController()
                val userIsInSomeRoom = runBlocking { viewModel.userIsInSomeRoom() }
                val startDestination = if (userIsInSomeRoom)
                    Destination.RoomDetails
                else
                    Destination.CreateRoom
                AnimatedNavHost(
                    navController = navController,
                    startDestination = startDestination.baseRoute
                ) {
                    createRoomScreenImpl(navGraphBuilder = this,
                        navController,
                        showError = { showError(it) })
                    roomDetailsScreenImpl(navGraphBuilder = this,
                        navController,
                        showError = { showError(it) })
                    accessRoomScreenImpl(navGraphBuilder = this,
                        navController,
                        showError = { showError(it) })
                }
            }
        }
    }

    private fun showError(message: String) {
        //TODO Mostrar o erro de uma forma mais bonita
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun requestUserAuthentication() {
        Intent(
            Intent.ACTION_VIEW,
            spotifyAuthorizationRequest.authorizationRequestUri
        ).run { startActivity(this) }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.let { data ->
            spotifyAuthorizationRequest.getResult(data)?.let { code ->
                viewModel.authenticateUserOnSpotify(code)
            }
        }
    }
}