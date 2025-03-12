package com.tapdoo.domain.usecase

import com.tapdoo.domain.model.BookApiModel
import com.tapdoo.domain.repository.BookApi

/**
 * Use case for retrieving detailed information about a specific book.
 *
 * This class encapsulates the logic for fetching book details from the [BookApi].
 * It acts as an intermediary between the presentation layer (e.g., UI) and the data layer (BookApi).
 *
 * @property bookApi The [BookApi] instance used to interact with the book data source.
 */
class GetBookDetailUseCase(private val bookApi: BookApi) {

    suspend operator fun invoke(bookId: String): Result<BookApiModel> {
        return bookApi.getBookDetail(bookId)
    }
}