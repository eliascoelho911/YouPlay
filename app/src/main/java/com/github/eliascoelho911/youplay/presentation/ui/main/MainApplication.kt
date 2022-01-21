package com.github.eliascoelho911.youplay.presentation.ui.main

import android.app.Application
import com.github.eliascoelho911.youplay.domain.di.domainCommonModule
import com.github.eliascoelho911.youplay.domain.di.useCasesModule
import com.github.eliascoelho911.youplay.domain.di.utilModule
import com.github.eliascoelho911.youplay.global.di.globalCommonModule
import com.github.eliascoelho911.youplay.infrastructure.di.cachesModule
import com.github.eliascoelho911.youplay.infrastructure.di.firebaseModule
import com.github.eliascoelho911.youplay.infrastructure.di.interceptorsModule
import com.github.eliascoelho911.youplay.infrastructure.di.repositoriesModule
import com.github.eliascoelho911.youplay.infrastructure.di.retrofitModule
import com.github.eliascoelho911.youplay.infrastructure.di.servicesModule
import com.github.eliascoelho911.youplay.infrastructure.di.sessionModule
import com.github.eliascoelho911.youplay.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(viewModelModule,
                useCasesModule,
                domainCommonModule,
                globalCommonModule,
                utilModule,
                repositoriesModule,
                firebaseModule,
                sessionModule,
                cachesModule,
                retrofitModule,
                interceptorsModule,
                servicesModule)
            androidContext(this@MainApplication)
        }
    }
}