package com.github.eliascoelho911.youplay.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.github.eliascoelho911.youplay.R
import com.github.eliascoelho911.youplay.domain.exceptions.DomainErrorException
import com.github.eliascoelho911.youplay.presentation.navigation.Destination
import com.github.eliascoelho911.youplay.presentation.ui.screens.accessroom.accessRoomScreenImpl
import com.github.eliascoelho911.youplay.presentation.ui.screens.home.homeScreenImpl
import com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails.roomDetailsScreenImpl
import com.github.eliascoelho911.youplay.presentation.ui.theme.YouPlayTheme
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.let { data ->
            spotifyAuthorizationRequest.getResult(data)?.let { code ->
                viewModel.authenticateUserOnSpotify(code)
            }
        }
    }

    fun showError(throwable: Throwable) {
        //TODO Mostrar o erro de uma forma mais bonita
        val message = if (throwable is DomainErrorException && throwable.message != null) {
            throwable.message
        } else {
            getString(R.string.error_default)
        }
        Toast.makeText(this, message ?: message, Toast.LENGTH_SHORT).show()
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
                val startDestination = startDestinationConsidering(userIsInSomeRoom)
                AnimatedNavHost(
                    navController = navController,
                    startDestination = startDestination.baseRoute
                ) {
                    homeScreenImpl(navGraphBuilder = this,
                        navController)
                    roomDetailsScreenImpl(navGraphBuilder = this,
                        navController)
                    accessRoomScreenImpl(navGraphBuilder = this,
                        navController)
                }
            }
        }
    }

    @Composable
    private fun startDestinationConsidering(userIsInSomeRoom: Boolean) = if (userIsInSomeRoom)
        Destination.RoomDetails
    else
        Destination.Home

    private fun requestUserAuthentication() {
        Intent(
            Intent.ACTION_VIEW,
            spotifyAuthorizationRequest.authorizationRequestUri
        ).run { startActivity(this) }
    }
}