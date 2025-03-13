package com.tapdoo.domain.usecase

import com.tapdoo.domain.model.BookDetail
import com.tapdoo.domain.repository.BookApi

/**
 * Use case for retrieving detailed information about a specific book.
 *
 * This class encapsulates the logic for fetching detailed book data from the [BookApi].
 * It uses the book's unique ID to request the information and returns the result wrapped in a [Result] object.
 *
 * @property bookApi The [BookApi] instance responsible for making network requests to fetch book data.
 */
class GetBookDetailUseCase(private val bookApi: BookApi) {

    suspend operator fun invoke(bookId: Int): Result<BookDetail> {
        return bookApi.getBookDetail(bookId)
    }
}