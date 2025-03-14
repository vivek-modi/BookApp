package com.tapdoo.data.mapper

import com.tapdoo.domain.model.BookDetailApiModel
import com.tapdoo.domain.repository.CurrencyService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

/**
 * Test case for [BookDetailMapper] class.
 */
class BookDetailMapperTest : KoinTest {

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        stopKoin() // Stop Koin to free memory
    }

    @Test
    fun `mapToBookDetail should correctly map BookDetailApiModel to BookDetail`() {
        // Arrange
        val mockCurrencyService = mockk<CurrencyService>()
        val bookDetailMapper = BookDetailMapper(mockCurrencyService)
        val apiModel = BookDetailApiModel(
            id = 1,
            title = "Test Book",
            description = "This is a test book.",
            isbn = "1234567890",
            price = 19,
            currencyCode = "USD",
            author = "Test Author"
        )
        val expectedPriceWithCurrency = "$19.99"
        every {
            mockCurrencyService.getPriceWithCurrency(19, "USD")
        } returns expectedPriceWithCurrency

        // Act
        val result = bookDetailMapper.mapToBookDetail(apiModel)

        // Assert
        assertEquals(1, result.id)
        assertEquals("Test Book", result.title)
        assertEquals("This is a test book.", result.description)
        assertEquals("1234567890", result.isbn)
        assertEquals(expectedPriceWithCurrency, result.priceWithCurrency)
        assertEquals("Test Author", result.author)

        verify { mockCurrencyService.getPriceWithCurrency(19, "USD") }
    }
}