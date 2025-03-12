package com.tapdoo.coroutine

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.lazyModule
import org.koin.dsl.module

val coroutineModule = lazyModule {  // Or name it dispatchersModule
    single { Dispatchers.IO }
}