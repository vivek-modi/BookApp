package com.tapdoo.domain.model

/**
 * Represents the detailed information of a book.
 *
 * This data class encapsulates the various attributes associated with a specific book,
 * such as its ID, title, description, ISBN, price with currency, and author.
 *
 * @property id The unique identifier of the book.
 * @property title The title of the book.
 * @property description A detailed description of the book's content.
 * @property isbn The International Standard Book Number (ISBN) of the book.
 * @property priceWithCurrency The price of the book along with its currency (e.g., "$19.99", "€15.00").
 * @property author The author of the book.
 */
data class BookDetail(
    val id: Int,
    val title: String,
    val description: String,
    val isbn: String,
    val priceWithCurrency: String,
    val author: String,
)
