package com.tapdoo.domain.usecase

import com.tapdoo.domain.model.BookApiModel
import com.tapdoo.domain.repository.BookApi

/**
 * Use case responsible for retrieving a list of books from the [BookApi].
 *
 * This class encapsulates the logic of fetching book data, providing a clean
 * and testable way to interact with the API. It uses the [BookApi] to perform
 * the actual network request and returns a [Result] object to handle potential
 * success or failure scenarios.
 *
 * @property bookApi The [BookApi] instance used to communicate with the backend.
 */
class GetBooksUseCase(private val bookApi: BookApi) {

    suspend operator fun invoke(): Result<List<BookApiModel>> {
        return bookApi.getBooks()
    }
}