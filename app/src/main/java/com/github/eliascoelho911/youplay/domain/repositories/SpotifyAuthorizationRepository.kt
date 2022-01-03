package com.github.eliascoelho911.youplay.domain.repositories

interface SpotifyAuthorizationRepository {
    suspend fun getAccessToken(): String?

    suspend fun addRefreshToken(code: String): String
}