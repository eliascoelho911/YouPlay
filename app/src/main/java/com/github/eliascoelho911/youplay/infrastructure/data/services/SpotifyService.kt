package com.github.eliascoelho911.youplay.infrastructure.data.services

import com.github.eliascoelho911.youplay.infrastructure.data.bodies.SpotifyUserBody
import retrofit2.http.GET

interface SpotifyService {
    @GET("v1/me")
    suspend fun loggedUser(): SpotifyUserBody
}