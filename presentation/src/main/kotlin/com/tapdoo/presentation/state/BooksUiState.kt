package com.tapdoo.presentation.state

import com.tapdoo.domain.model.BookApiModel

data class BooksUiState(
    val isLoading: Boolean = true,
    val books: List<BookApiModel> = emptyList(),
    val error: Throwable? = null
)