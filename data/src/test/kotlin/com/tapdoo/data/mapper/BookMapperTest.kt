package com.tapdoo.data.mapper

import com.tapdoo.domain.model.Book
import com.tapdoo.domain.model.BookApiModel
import com.tapdoo.domain.repository.CurrencyService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class BookMapperTest : KoinTest {

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        stopKoin() // Stop Koin to free memory
    }

    @Test
    fun `mapToBooks maps BookApiModel to Book correctly`() {
        // Given
        val currencyService = mockk<CurrencyService>()
        val bookMapper = BookMapper(currencyService)
        val bookApiModel = BookApiModel(
            id = 1,
            title = "Test Book",
            isbn = "123-456-789",
            price = 999,
            currencyCode = "USD",
            author = "Test Author"
        )
        val bookApiModelList = listOf(bookApiModel)
        val expectedBook = Book(
            id = 1,
            title = "Test Book",
            isbn = "123-456-789",
            priceWithCurrency = "$99.99",
            author = "Test Author"
        )
        every {
            currencyService.getPriceWithCurrency(999, "USD")
        } returns "$99.99"

        // When
        val result = bookMapper.mapToBooks(bookApiModelList)

        // Then
        assertEquals(listOf(expectedBook), result)
    }
}