package com.github.eliascoelho911.youplay.infrastructure.data.caches

import com.github.eliascoelho911.youplay.infrastructure.data.bodies.SpotifyUserBody

class UserLoggedInSpotifyCache {
    private var cached: SpotifyUserBody? = null

    fun get(): SpotifyUserBody? {
        return cached
    }

    fun put(user: SpotifyUserBody) {
        cached = user
    }
}