package com.tapdoo.data.di

import com.tapdoo.data.mapper.BookMapper
import com.tapdoo.data.repository.BookRemoteDataSource
import com.tapdoo.data.service.RealCurrencyService
import com.tapdoo.domain.repository.BookApi
import com.tapdoo.domain.repository.CurrencyService
import com.tapdoo.domain.usecase.GetBookDetailUseCase
import com.tapdoo.domain.usecase.GetBooksUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.lazyModule

val dataModule = lazyModule {
    singleOf(::BookRemoteDataSource) {
        bind<BookApi>()
    }
    singleOf(::GetBooksUseCase)
    singleOf(::GetBookDetailUseCase)
    singleOf(::RealCurrencyService) {
        bind<CurrencyService>()
    }
    singleOf(::BookMapper)
}