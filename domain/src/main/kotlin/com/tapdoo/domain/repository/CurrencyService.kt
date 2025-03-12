package com.tapdoo.domain.repository

/**
 * Interface for providing currency-related services.
 *
 * This interface defines methods for interacting with currency data, such as retrieving
 * the price of an item in a specific currency.
 */
interface CurrencyService {
    fun getPriceWithCurrency(currencyValue: Int, currencyCode: String): String
}