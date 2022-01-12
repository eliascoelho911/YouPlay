package com.github.eliascoelho911.youplay.domain.di

import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.DeleteRoomById
import com.github.eliascoelho911.youplay.domain.usecases.room.GetRoomById
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveRoomById
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateRoom
import com.github.eliascoelho911.youplay.domain.usecases.session.GetAuthSessionId
import com.github.eliascoelho911.youplay.domain.usecases.session.GetCurrentRoomId
import com.github.eliascoelho911.youplay.domain.usecases.session.PutAuthSessionId
import com.github.eliascoelho911.youplay.domain.usecases.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AddSpotifyRefreshToken
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AuthenticateUserOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.spotify.UserIsAuthenticatedOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.domain.usecases.user.UserExitFromRoom
import org.koin.dsl.module

val useCasesModule = module {
    single { GetLoggedUser(get()) }
    single { GetAuthSessionId(get()) }
    single { GetCurrentRoomId(get()) }
    single { GetRoomById(get()) }
    single { GetCurrentRoom(get(), get()) }
    single { ObserveCurrentRoom(get(), get()) }
    single { ObserveCurrentMusic(get()) }
    single { ObserveRoomById(get()) }
    single { PutCurrentRoomId(get()) }
    single { CreateNewRoom(get(), get()) }
    single { DeleteRoomById(get()) }
    single { UpdateRoom(get()) }
    single { UserExitFromRoom(get(), get(), get(), get()) }
    single { PutAuthSessionId(get()) }
    single { AddSpotifyRefreshToken(get()) }
    single { AuthenticateUserOnSpotify(get(), get()) }
    single { UserIsAuthenticatedOnSpotify(get()) }
}