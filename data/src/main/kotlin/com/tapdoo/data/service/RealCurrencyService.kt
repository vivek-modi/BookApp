package com.tapdoo.data.service

import com.tapdoo.domain.repository.CurrencyService
import java.util.Currency

/**
 * Service class responsible for handling currency-related operations.
 */
class RealCurrencyService : CurrencyService {
    override fun getCurrencySymbol(currencyCode: String): String {
        return try {
            Currency.getInstance(currencyCode).symbol
        } catch (e: IllegalArgumentException) {
            currencyCode // Fallback to ISO code
        }
    }
}