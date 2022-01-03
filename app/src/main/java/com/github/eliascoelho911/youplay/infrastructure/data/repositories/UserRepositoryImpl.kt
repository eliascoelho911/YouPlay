package com.github.eliascoelho911.youplay.infrastructure.data.repositories

import com.github.eliascoelho911.youplay.common.emitSuccess
import com.github.eliascoelho911.youplay.common.flowResource
import com.github.eliascoelho911.youplay.domain.entities.User
import com.github.eliascoelho911.youplay.domain.repositories.UserRepository
import com.github.eliascoelho911.youplay.infrastructure.data.caches.UserLoggedInSpotifyCache
import com.github.eliascoelho911.youplay.infrastructure.data.mappers.toDomainUser
import com.github.eliascoelho911.youplay.infrastructure.data.services.SpotifyService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.retry
import retrofit2.HttpException

class UserRepositoryImpl(
    private val userLoggedInSpotifyCache: UserLoggedInSpotifyCache,
    private val spotifyService: SpotifyService,
) : UserRepository {
    override val loggedUser = flowResource<User> {
        userLoggedInSpotifyCache.get()?.let { emitSuccess(it.toDomainUser()) }

        spotifyService.loggedUser().let { response ->
            userLoggedInSpotifyCache.put(response)
            emitSuccess(response.toDomainUser())
        }
    }.retry(retries = 5) {
        if (it is HttpException) {
            delay(2000)
            true
        } else {
            false
        }
    }
}