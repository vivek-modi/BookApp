package com.tapadoo.bookapp

import android.app.Application
import com.tapdoo.coroutine.coroutineModule
import com.tapdoo.data.di.dataModule
import com.tapdoo.network.di.networkModule
import com.tapdoo.presentation.di.bookModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

class BookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initialiseKoin()
    }

    private fun initialiseKoin() {
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@BookApplication)
            // Load modules
            lazyModules(
                networkModule,
                coroutineModule,
                dataModule,
                bookModule,
            )
        }
    }
}