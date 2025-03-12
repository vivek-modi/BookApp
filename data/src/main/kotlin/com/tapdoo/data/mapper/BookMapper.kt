package com.tapdoo.data.mapper

import com.tapdoo.domain.model.Book
import com.tapdoo.domain.model.BookApiModel
import com.tapdoo.domain.repository.CurrencyService

/**
 * The `BookMapper` class is responsible for mapping a list of `BookApiModel` objects
 * to a list of `Book` objects. It leverages the `CurrencyService` to handle currency
 * conversions and formatting when creating the `Book` objects.
 *
 * @property currencyService The service used for currency-related operations, such as
 *                           getting the formatted price with the correct currency symbol.
 */
class BookMapper(private val currencyService: CurrencyService) {

    fun mapToBooks(bookApiEntries: List<BookApiModel>): List<Book> {
        return bookApiEntries.map { bookApiItem ->
            bookApiItem.toBook()
        }
    }

    private fun BookApiModel.toBook(): Book {
        return Book(
            id = this.id,
            title = this.title,
            isbn = this.isbn,
            priceWithCurrency = currencyService.getPriceWithCurrency(
                this.price,
                this.currencyCode
            ),
            author = this.author,
        )
    }
}