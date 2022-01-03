package com.github.eliascoelho911.youplay.infrastructure.retrofit

import retrofit2.Retrofit


class SpotifyRetrofit(val retrofit: Retrofit) {
    companion object {
        const val BaseUrl = "https://api.spotify.com/"
    }
}