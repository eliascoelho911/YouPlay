package com.github.eliascoelho911.youplay.infrastructure.interceptors

import com.github.eliascoelho911.youplay.domain.repositories.SpotifyAuthorizationRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import kotlin.coroutines.CoroutineContext

class SpotifyAuthorizationInterceptor(
    private val spotifyAuthorizationRepository: SpotifyAuthorizationRepository,
    private val coroutineContext: CoroutineContext
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest(): Request {
        val accessToken = runBlocking(coroutineContext) {
            spotifyAuthorizationRepository.getAccessToken()
        }
        return newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }
}