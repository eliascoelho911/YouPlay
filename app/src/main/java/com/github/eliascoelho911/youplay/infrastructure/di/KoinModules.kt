package com.github.eliascoelho911.youplay.infrastructure.di

import com.github.eliascoelho911.youplay.domain.repositories.RoomRepository
import com.github.eliascoelho911.youplay.domain.repositories.SpotifyAuthorizationRepository
import com.github.eliascoelho911.youplay.domain.repositories.UserRepository
import com.github.eliascoelho911.youplay.domain.session.ApplicationSession
import com.github.eliascoelho911.youplay.infrastructure.data.caches.SpotifyAccessTokenCache
import com.github.eliascoelho911.youplay.infrastructure.data.caches.UserLoggedInSpotifyCache
import com.github.eliascoelho911.youplay.infrastructure.data.repositories.RoomRepositoryImpl
import com.github.eliascoelho911.youplay.infrastructure.interceptors.SpotifyAuthorizationInterceptor
import com.github.eliascoelho911.youplay.infrastructure.data.repositories.SpotifyAuthorizationRepositoryImpl
import com.github.eliascoelho911.youplay.infrastructure.data.repositories.UserRepositoryImpl
import com.github.eliascoelho911.youplay.infrastructure.retrofit.FunctionsRetrofit
import com.github.eliascoelho911.youplay.infrastructure.retrofit.SpotifyRetrofit
import com.github.eliascoelho911.youplay.infrastructure.data.services.FunctionsService
import com.github.eliascoelho911.youplay.infrastructure.data.services.SpotifyService
import com.github.eliascoelho911.youplay.infrastructure.data.session.ApplicationSessionImpl
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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