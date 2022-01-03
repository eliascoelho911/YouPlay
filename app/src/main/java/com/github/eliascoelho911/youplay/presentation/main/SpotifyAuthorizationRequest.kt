package com.github.eliascoelho911.youplay.presentation.main

import android.net.Uri
import androidx.core.net.toUri
import com.github.eliascoelho911.youplay.infrastructure.constants.DeeplinkUris
import com.github.eliascoelho911.youplay.infrastructure.constants.SpotifyConstants

class SpotifyAuthorizationRequest {
    val authorizationRequestUri = ("${SpotifyConstants.AccountsBaseUrl}authorize?" +
            "client_id=${SpotifyConstants.ClientId}&" +
            "redirect_uri=${DeeplinkUris.AuthenticationCallback}&" +
            "response_type=code&" +
            "scope=app-remote-control").toUri()

    fun getResult(data: Uri) = if (isCallback(data)) data.getQueryParameter("code") else null

    private fun isCallback(data: Uri) =
        data.toString().contains(DeeplinkUris.AuthenticationCallback)
}