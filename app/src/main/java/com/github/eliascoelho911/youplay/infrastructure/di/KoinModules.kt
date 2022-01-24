package com.github.eliascoelho911.youplay.infrastructure.di

import com.github.eliascoelho911.youplay.domain.common.room.DeleteCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.room.DeleteRoomById
import com.github.eliascoelho911.youplay.domain.common.room.GetRoomById
import com.github.eliascoelho911.youplay.domain.common.room.ObserveRoomById
import com.github.eliascoelho911.youplay.domain.common.room.UpdateCurrentRoom
import com.github.eliascoelho911.youplay.domain.common.room.UpdateRoom
import com.github.eliascoelho911.youplay.domain.common.session.GetAuthSessionId
import com.github.eliascoelho911.youplay.domain.common.session.GetCurrentRoomId
import com.github.eliascoelho911.youplay.domain.common.session.PutAuthSessionId
import com.github.eliascoelho911.youplay.domain.common.session.PutCurrentRoomId
import com.github.eliascoelho911.youplay.domain.common.spotify.AddSpotifyRefreshToken
import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.domain.repositories.SpotifyAuthorizationRepository
import com.github.eliascoelho911.youplay.domain.repositories.UserRepository
import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import com.github.eliascoelho911.youplay.domain.usecases.room.CreateNewRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.GetCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentMusic
import com.github.eliascoelho911.youplay.domain.usecases.room.ObserveCurrentRoom
import com.github.eliascoelho911.youplay.domain.usecases.spotify.AuthenticateUserOnSpotify
import com.github.eliascoelho911.youplay.domain.usecases.user.EnterTheRoom
import com.github.eliascoelho911.youplay.domain.usecases.user.GetLoggedUser
import com.github.eliascoelho911.youplay.domain.usecases.user.UserExitFromRoom
import com.github.eliascoelho911.youplay.domain.util.room.CheckIfRoomExistsById
import com.github.eliascoelho911.youplay.domain.util.spotify.UserIsAuthenticatedOnSpotify
import com.github.eliascoelho911.youplay.domain.util.user.UserIsInSomeRoom
import com.github.eliascoelho911.youplay.infrastructure.data.caches.SpotifyAccessTokenCache
import com.github.eliascoelho911.youplay.infrastructure.data.caches.UserLoggedInSpotifyCache
import com.github.eliascoelho911.youplay.infrastructure.data.repositories.RoomRepositoryImpl
import com.github.eliascoelho911.youplay.infrastructure.data.repositories.SpotifyAuthorizationRepositoryImpl
import com.github.eliascoelho911.youplay.infrastructure.data.repositories.UserRepositoryImpl
import com.github.eliascoelho911.youplay.infrastructure.data.services.FunctionsService
import com.github.eliascoelho911.youplay.infrastructure.data.services.SpotifyService
import com.github.eliascoelho911.youplay.infrastructure.data.session.ApplicationSessionImpl
import com.github.eliascoelho911.youplay.infrastructure.interceptors.SpotifyAuthorizationInterceptor
import com.github.eliascoelho911.youplay.infrastructure.retrofit.FunctionsRetrofit
import com.github.eliascoelho911.youplay.infrastructure.retrofit.SpotifyRetrofit
import com.github.eliascoelho911.youplay.presentation.ui.main.MainViewModel
import com.github.eliascoelho911.youplay.presentation.ui.screens.accessroom.AccessRoomViewModel
import com.github.eliascoelho911.youplay.presentation.ui.screens.home.HomeViewModel
import com.github.eliascoelho911.youplay.presentation.ui.screens.roomdetails.RoomDetailsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference

object InfrastructureModules {
    val repositoriesModule = module {
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
        single<SpotifyAuthorizationRepository> {
            SpotifyAuthorizationRepositoryImpl(get(), get(), get())
        }
        single<RoomRepository> { RoomRepositoryImpl(get()) }
    }

    val firebaseModule = module {
        single { FirebaseFirestore.getInstance() }
    }

    val sessionModule = module {
        single<ApplicationSession> { ApplicationSessionImpl(get()) }
    }

    val cachesModule = module {
        single { UserLoggedInSpotifyCache() }
        single { SpotifyAccessTokenCache() }
    }

    val retrofitModule = module {
        single {
            HttpLoggingInterceptor().let { logging ->
                logging.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder().addInterceptor(logging).build()
            }
        }
        single {
            Retrofit.Builder()
                .baseUrl(FunctionsRetrofit.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build().run { FunctionsRetrofit(this) }
        }
        single {
            Retrofit.Builder()
                .baseUrl(SpotifyRetrofit.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get<OkHttpClient>().newBuilder()
                    .addInterceptor(get<SpotifyAuthorizationInterceptor>())
                    .build())
                .build().run { SpotifyRetrofit(this) }
        }
    }

    val interceptorsModule = module {
        single { SpotifyAuthorizationInterceptor(get(), Dispatchers.IO) }
    }

    val servicesModule = module {
        single<FunctionsService> { get<FunctionsRetrofit>().retrofit.create(FunctionsService::class.java) }
        single<SpotifyService> { get<SpotifyRetrofit>().retrofit.create(SpotifyService::class.java) }
    }
}

object DomainModules {
    val useCasesModule = module {
        single { GetLoggedUser(get()) }
        single { GetCurrentRoom(get(), get()) }
        single { ObserveCurrentRoom(get(), get()) }
        single { ObserveCurrentMusic(get()) }
        single { CreateNewRoom(get(), get(), get(), get()) }
        single { EnterTheRoom(get(), get(), get(), get(), get()) }
        single { UserExitFromRoom(get(), get(), get(), get(), get(), get()) }
        single { AuthenticateUserOnSpotify(get(), get(), get()) }
    }

    val commonModule = module {
        single { GetAuthSessionId(get()) }
        single { GetCurrentRoomId(get()) }
        single { GetRoomById(get()) }
        single { ObserveRoomById(get()) }
        single { PutCurrentRoomId(get()) }
        single { DeleteRoomById(get()) }
        single { UpdateRoom(get()) }
        single { DeleteCurrentRoom(get(), get()) }
        single { UpdateCurrentRoom(get(), get()) }
        single { PutAuthSessionId(get()) }
        single { AddSpotifyRefreshToken(get()) }
    }

    val utilModule = module {
        single { CheckIfRoomExistsById(get()) }
        single { UserIsInSomeRoom(get()) }
        single { UserIsAuthenticatedOnSpotify(get()) }
    }
}

object PresentationModules {
    val viewModelModule = module {
        viewModel {
            HomeViewModel(get(), get(), context = WeakReference(get()), get())
        }
        viewModel {
            RoomDetailsViewModel(get(), get(), get(), get(), get())
        }
        viewModel {
            MainViewModel(get(), get(), get(), get())
        }
        viewModel {
            AccessRoomViewModel(get())
        }
    }
}