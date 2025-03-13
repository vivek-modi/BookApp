package com.tapdoo.presentation.state

import com.tapdoo.domain.model.BookDetail

data class BookDetailUiState(
    val isLoading: Boolean = true,
    val bookDetail: BookDetail? = null,
    val error: String? = null,
)
