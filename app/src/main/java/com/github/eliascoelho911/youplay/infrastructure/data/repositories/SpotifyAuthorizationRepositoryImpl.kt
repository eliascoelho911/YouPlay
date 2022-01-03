package com.github.eliascoelho911.youplay.infrastructure.data.repositories

import com.github.eliascoelho911.youplay.domain.repositories.SpotifyAuthorizationRepository
import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import com.github.eliascoelho911.youplay.infrastructure.constants.DeeplinkUris
import com.github.eliascoelho911.youplay.infrastructure.data.caches.SpotifyAccessTokenCache
import com.github.eliascoelho911.youplay.infrastructure.data.services.FunctionsService

class SpotifyAuthorizationRepositoryImpl(
    private val applicationSession: ApplicationSession,
    private val functionsService: FunctionsService,
    private val spotifyAccessTokenCache: SpotifyAccessTokenCache,
) : SpotifyAuthorizationRepository {

    override suspend fun getAccessToken(): String? {
        spotifyAccessTokenCache.get()?.let { return it }

        return applicationSession.getAuthId()?.let { id ->
            val accessToken = functionsService.getSpotifyAccessToken(id).accessToken
            spotifyAccessTokenCache.put(accessToken)
            accessToken
        }
    }

    override suspend fun addRefreshToken(code: String) =
        functionsService.addSpotifyRefreshToken(code, DeeplinkUris.AuthenticationCallback).id
}