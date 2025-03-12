package com.tapdoo.domain.repository

/**
 * Interface for providing currency-related services.
 *
 * This interface defines methods for retrieving information about currencies,
 * such as their symbols.
 */
interface CurrencyService {
    fun getCurrencySymbol(currencyCode: String): String
}