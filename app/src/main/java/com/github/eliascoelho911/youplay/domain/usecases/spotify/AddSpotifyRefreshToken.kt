package com.github.eliascoelho911.youplay.domain.usecases.spotify

import com.github.eliascoelho911.youplay.domain.repositories.SpotifyAuthorizationRepository

class AddSpotifyRefreshToken(
    private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository,
) {
    suspend fun add(code: String) =
        spotifyAuthorizationRepository.addRefreshToken(code)
}