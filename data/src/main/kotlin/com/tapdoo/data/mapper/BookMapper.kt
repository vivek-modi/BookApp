package com.tapdoo.data.mapper

import com.tapdoo.domain.model.Book
import com.tapdoo.domain.model.BookApiModel
import com.tapdoo.domain.repository.CurrencyService

/**
 * The `BookMapper` class is responsible for mapping data from the API model (`BookApiModel`)
 * to the domain model (`Book`). It utilizes a `CurrencyService` to retrieve the
 * currency symbol based on the currency code provided in the API data.
 *
 * @property currencyService The `CurrencyService` instance used to fetch currency symbols.
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
            price = this.price,
            currencySymbol = currencyService.getCurrencySymbol(this.currencyCode),
            author = this.author,
        )
    }
}