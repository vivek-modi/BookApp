package com.tapdoo.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapdoo.domain.usecase.GetBookDetailUseCase
import com.tapdoo.presentation.state.BookDetailUiState
import kotlinx.coroutines.launch

class BookDetailViewModel(private val getBookDetailUseCase: GetBookDetailUseCase) : ViewModel() {

    var bookDetailUiState by mutableStateOf(BookDetailUiState())
        private set

    fun getBookDetail(bookId: Int) {
        viewModelScope.launch {
            val result = getBookDetailUseCase(bookId)
            result.onSuccess { bookDetail ->
                bookDetailUiState = bookDetailUiState.copy(bookDetail = bookDetail)
            }.onFailure { error ->
                Log.e("BooksViewModel", "$error")
                bookDetailUiState = bookDetailUiState.copy(error = error)
            }
            bookDetailUiState = bookDetailUiState.copy(isLoading = false)
        }
    }
}