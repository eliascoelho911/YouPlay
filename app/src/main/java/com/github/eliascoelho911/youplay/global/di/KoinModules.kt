package com.github.eliascoelho911.youplay.global.di

import com.github.eliascoelho911.youplay.global.Messages
import org.koin.dsl.module

val globalCommonModule = module {
    single { Messages.Error(get()) }
}