package com.tapdoo.domain.model

/**
 * Represents a book with its essential details.
 *
 * @property id The unique identifier of the book.
 * @property title The title of the book.
 * @property isbn The ISBN (International Standard Book Number) of the book.
 * @property priceWithCurrency The price of the book including its currency (e.g., "$19.99", "€15.50").
 * @property author The author of the book.
 */
data class Book(
    val id: Int,
    val title: String,
    val isbn: String,
    val priceWithCurrency: String,
    val author: String,
)