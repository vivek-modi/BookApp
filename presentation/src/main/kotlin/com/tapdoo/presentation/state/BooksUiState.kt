package com.tapdoo.presentation.state

import com.tapdoo.domain.model.Book

data class BooksUiState(
    val isLoading: Boolean = true,
    val books: List<Book> = emptyList(),
    val error: Throwable? = null,
)