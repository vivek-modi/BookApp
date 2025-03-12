package com.tapdoo.domain.repository

import com.tapdoo.domain.model.Book
import com.tapdoo.domain.model.BookApiModel

interface BookApi {

    /**
     * Retrieves a list of books.
     *
     * @return A Result containing either a list of Book objects or an error.
     */
    suspend fun getBooks(): Result<List<Book>>

    /**
     * Retrieves the details of a specific book.
     *
     * @param bookId The ID of the book to retrieve.
     * @return A Result containing either the Book object or an error.
     */
    suspend fun getBookDetail(bookId: String): Result<BookApiModel>
}