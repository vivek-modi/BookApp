package com.tapdoo.domain.model

import kotlinx.serialization.Serializable

/**
 * Represents the detailed information of a book as received from the API.
 *
 * This data class holds the essential properties of a book, including its unique identifier,
 * title, description, ISBN, price, currency, and author. It is designed to be used for
 * transferring book details data between the API and the application.
 *
 * @property id The unique identifier of the book.
 * @property title The title of the book.
 * @property description A brief description or summary of the book's content.
 * @property isbn The International Standard Book Number (ISBN) of the book.
 * @property price The price of the book.
 * @property currencyCode The currency code (e.g., USD, EUR) in which the price is specified.
 * @property author The author of the book.
 */
@Serializable
data class BookDetailApiModel(
    val id: Int,
    val title: String,
    val description: String,
    val isbn: String,
    val price: Int,
    val currencyCode: String,
    val author: String,
)