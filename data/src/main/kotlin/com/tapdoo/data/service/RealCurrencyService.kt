package com.tapdoo.data.service

import com.tapdoo.domain.repository.CurrencyService
import java.text.NumberFormat.getCurrencyInstance
import java.util.Currency

/**
 * Implementation of the [CurrencyService] interface that provides real currency formatting.
 *
 * This class handles the conversion of integer currency values (assumed to be in cents)
 * to a formatted string representation using the specified currency code.
 */
class RealCurrencyService : CurrencyService {

    override fun getPriceWithCurrency(currencyValue: Int, currencyCode: String): String {
        return try {
            val format = getCurrencyInstance()
            format.currency = Currency.getInstance(currencyCode)
            format.format(currencyValue / 100.0)
        } catch (e: IllegalArgumentException) {
            "$currencyValue $currencyCode" // Fallback to ISO code
        }
    }
}