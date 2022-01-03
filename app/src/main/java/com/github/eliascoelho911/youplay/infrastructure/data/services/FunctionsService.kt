package com.github.eliascoelho911.youplay.infrastructure.data.services

import com.github.eliascoelho911.youplay.infrastructure.data.bodies.SpotifyAccessTokenBody
import com.github.eliascoelho911.youplay.infrastructure.data.bodies.SpotifyPutRefreshTokenBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FunctionsService {
    @POST("/addRefreshToken")
    suspend fun addSpotifyRefreshToken(
        @Header("code") code: String,
        @Header("redirectUri") redirectUri: String,
    ): SpotifyPutRefreshTokenBody

    @GET("/getAccessToken")
    suspend fun getSpotifyAccessToken(
        @Header("id") id: String,
    ): SpotifyAccessTokenBody
}