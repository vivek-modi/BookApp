package com.tapdoo.domain.model

import kotlinx.serialization.Serializable

/**
 * Represents a book in the API response.
 *
 * This data class is designed to model the structure of a book object
 * as it is received from the API. It includes essential information
 * about the book such as its unique identifier, title, price, currency,
 * and author.
 *
 * @property id The unique identifier of the book.
 * @property title The title of the book.
 * @property price The price of the book.
 * @property currencyCode The currency code for the book's price (e.g., USD, EUR).
 * @property author The author of the book.
 */
@Serializable
data class BookApiModel(
    val id: Int,
    val title: String,
    val isbn: String,
    val price: Int,
    val currencyCode: String,
    val author: String,
)