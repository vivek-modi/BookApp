package com.tapdoo.network.di

import com.tapdoo.network.ktor.KtorClient
import com.tapdoo.network.ktor.KtorHttpService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.lazyModule

val networkModule = lazyModule {
    single { KtorClient().build() }
    singleOf(::KtorHttpService)
}