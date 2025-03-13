package com.tapdoo.data.mapper

import com.tapdoo.domain.model.BookDetail
import com.tapdoo.domain.model.BookDetailApiModel
import com.tapdoo.domain.repository.CurrencyService

/**
 * Mapper class responsible for transforming [BookDetailApiModel] to [BookDetail].
 * It utilizes the [CurrencyService] to format the price with the appropriate currency symbol.
 *
 * @property currencyService The service responsible for handling currency-related operations,
 *                           like formatting the price with the correct currency symbol.
 */
class BookDetailMapper(private val currencyService: CurrencyService) {

    fun mapToBookDetail(bookDetail: BookDetailApiModel): BookDetail {
        return BookDetail(
            id = bookDetail.id,
            title = bookDetail.title,
            description = bookDetail.description,
            isbn = bookDetail.isbn,
            priceWithCurrency = currencyService.getPriceWithCurrency(
                bookDetail.price,
                bookDetail.currencyCode
            ),
            author = bookDetail.author,
        )
    }
}