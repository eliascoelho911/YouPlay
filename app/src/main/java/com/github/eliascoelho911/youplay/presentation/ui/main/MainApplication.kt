package com.github.eliascoelho911.youplay.presentation.ui.main

import android.app.Application
import com.github.eliascoelho911.youplay.infrastructure.di.DomainModules
import com.github.eliascoelho911.youplay.infrastructure.di.InfrastructureModules
import com.github.eliascoelho911.youplay.infrastructure.di.PresentationModules
import com.github.eliascoelho911.youplay.infrastructure.di.UtilModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(DomainModules.useCasesModule,
                DomainModules.commonModule,
                DomainModules.utilModule,
                PresentationModules.viewModelModule,
                InfrastructureModules.repositoriesModule,
                InfrastructureModules.firebaseModule,
                InfrastructureModules.sessionModule,
                InfrastructureModules.cachesModule,
                InfrastructureModules.retrofitModule,
                InfrastructureModules.interceptorsModule,
                InfrastructureModules.servicesModule,
                UtilModules.module)
            androidContext(this@MainApplication)
        }
    }
}