package com.tapdoo.presentation.di

import com.tapdoo.presentation.viewmodel.BooksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.lazyModule

val bookModule = lazyModule {
    viewModelOf(::BooksViewModel)
}