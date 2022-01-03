package com.github.eliascoelho911.youplay.infrastructure.retrofit

import retrofit2.Retrofit

class FunctionsRetrofit(val retrofit: Retrofit) {
    companion object {
        const val BaseUrl = "https://us-central1-youplay-fd9e8.cloudfunctions.net/"
    }
}