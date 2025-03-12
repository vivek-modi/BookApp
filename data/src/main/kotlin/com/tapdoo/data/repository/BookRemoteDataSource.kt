package com.tapdoo.data.repository

import com.tapdoo.data.mapper.BookMapper
import com.tapdoo.domain.model.Book
import com.tapdoo.domain.model.BookApiModel
import com.tapdoo.domain.repository.BookApi
import com.tapdoo.network.ktor.KtorHttpService

class BookRemoteDataSource(
    private val ktorHttpService: KtorHttpService,
    private val bookMapper: BookMapper,
) : BookApi {

    private companion object {
        const val BOOK_URL = "/books"
    }

    /**
    -     * Retrieves a list of books from the remote API.
    -     *
    -     * @return A Result containing either a list of Book objects or an error.
     */
    override suspend fun getBooks(): Result<List<Book>> {
        return ktorHttpService.get<List<BookApiModel>>(BOOK_URL).map { apiModels ->
            bookMapper.mapToBooks(apiModels)
        }
    }

    /**
     * Retrieves details of a specific book from the remote API.
     *
     * @param bookId The ID of the book to retrieve.
     * @return A Result containing either the BookApiModel or an error.
     */
    override suspend fun getBookDetail(bookId: String): Result<BookApiModel> {
        return ktorHttpService.get<BookApiModel>("$BOOK_URL/$bookId")
    }
}