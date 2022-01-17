package com.github.eliascoelho911.youplay.domain.di

import com.github.eliascoelho911.youplay.domain.usecases.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.DeleteCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.DeleteRoomById
import com.github.eliascoelho911.youplay.domain.common.room.GetRoomById
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.room.ObserveRoomById
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.UpdateRoom
import com.github.eliascoelho911.youplay.domain.common.session.GetAuthSessionId
import com.github.eliascoelho911.youplay.domain.common.session.GetCurrentRoomId
import com.github.eliascoelho911.youplay.domain.common.session.PutAuthSessionId
import com.github.eliascoelho911.youplay.domain.common.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.common.spotify.AddSpotifyRefreshToken
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AuthenticateUserOnSpotify
import com.github.eliascoelho911.youplay.domain.common.spotify.UserIsAuthenticatedOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.domain.usecases.user.UserExitFromRoom
import com.github.eliascoelho911.youplay.domain.common.user.UserIsInSomeRoom
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
    single { DeleteCurrentRoom(get(), get()) }
    single { UpdateCurrentRoom(get(), get()) }
    single { EnterTheRoom(get(), get(), get()) }
    single { CheckIfRoomExistsById(get()) }
    single { UserIsInSomeRoom(get()) }
    single { UserExitFromRoom(get(), get(), get(), get(), get()) }
    single { PutAuthSessionId(get()) }
    single { AddSpotifyRefreshToken(get()) }
    single { AuthenticateUserOnSpotify(get(), get()) }
    single { UserIsAuthenticatedOnSpotify(get()) }
}