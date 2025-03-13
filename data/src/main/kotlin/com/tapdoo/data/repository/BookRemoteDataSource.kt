package com.tapdoo.data.repository

import com.tapdoo.data.mapper.BookDetailMapper
import com.tapdoo.data.mapper.BookMapper
import com.tapdoo.domain.model.Book
import com.tapdoo.domain.model.BookApiModel
import com.tapdoo.domain.model.BookDetail
import com.tapdoo.domain.model.BookDetailApiModel
import com.tapdoo.domain.repository.BookApi
import com.tapdoo.network.ktor.KtorHttpService

/**
 * A data source for interacting with remote APIs to fetch book and book detail information.
 *
 * @property httpService The HTTP service used to make API calls.
 * @property bookMapper A mapper to convert API models to domain models for books.
 * @property bookDetailMapper A mapper to convert API models to domain models for book details.
 */
class BookRemoteDataSource(
    private val httpService: KtorHttpService,
    private val bookMapper: BookMapper,
    private val bookDetailMapper: BookDetailMapper,
) : BookApi {

    private companion object {
        const val BOOKS_ENDPOINT = "/books"
        const val BOOKS_DETAIL_END_POINT = "/book"
    }

    /**
     * Fetches a list of books from the remote API.
     *
     * @return A [Result] containing a list of [Book] on success, or an error on failure.
     */
    override suspend fun getBooks(): Result<List<Book>> {
        return httpService.get<List<BookApiModel>>(BOOKS_ENDPOINT).map { bookApiModels ->
            bookMapper.mapToBooks(bookApiModels)
        }
    }

    /**
     * Fetches details of a specific book by its ID from the remote API.
     *
     * @param bookId The ID of the book whose details are to be fetched.
     * @return A [Result] containing the [BookDetail] on success, or an error on failure.
     */
    override suspend fun getBookDetail(bookId: Int): Result<BookDetail> {
        return httpService.get<BookDetailApiModel>("$BOOKS_DETAIL_END_POINT/$bookId")
            .map { bookDetailApiModel ->
                bookDetailMapper.mapToBookDetail(bookDetailApiModel)
            }
    }
}