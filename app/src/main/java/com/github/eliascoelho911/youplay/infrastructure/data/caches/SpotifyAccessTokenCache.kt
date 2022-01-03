package com.github.eliascoelho911.youplay.infrastructure.data.caches

import org.joda.time.LocalDateTime

private const val AccessTokenValidityInSeconds = 3600

class SpotifyAccessTokenCache {
    private var cached: String? = null
    private var lastUpdate: LocalDateTime? = null

    fun get(): String? {
        if (cached != null && currentAccessTokenIsExpired())
            cached = null
        return cached
    }

    fun put(newAccessToken: String) {
        cached = newAccessToken
        lastUpdate = LocalDateTime.now()
    }

    private fun currentAccessTokenIsExpired(): Boolean =
        lastUpdate?.plusSeconds(AccessTokenValidityInSeconds)?.let { dateExpiration ->
            LocalDateTime.now().isAfter(dateExpiration)
        } ?: true
}
