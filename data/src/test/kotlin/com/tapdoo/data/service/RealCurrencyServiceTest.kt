package com.tapdoo.data.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.text.NumberFormat

class RealCurrencyServiceTest : KoinTest {

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        stopKoin() // Stop Koin to free memory
    }

    @Test
    fun `getPriceWithCurrency should format currency correctly`() {
        mockkStatic(NumberFormat::class)
        val mockFormat = mockk<NumberFormat>()
        every { NumberFormat.getCurrencyInstance() } returns mockFormat
        every { mockFormat.currency = any() } returns Unit
        every { mockFormat.format(any<Double>()) } returns "$10.50"

        val service = RealCurrencyService()
        val result = service.getPriceWithCurrency(1050, "USD")
        assertEquals("$10.50", result)
    }

    @Test
    fun `getPriceWithCurrency should fallback to ISO code on exception`() {
        val service = RealCurrencyService()
        val result = service.getPriceWithCurrency(1050, "INVALID")
        assertEquals("1050 INVALID", result)
    }
}