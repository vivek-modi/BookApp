package com.tapdoo.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapdoo.domain.usecase.GetBooksUseCase
import com.tapdoo.presentation.state.BooksUiState
import kotlinx.coroutines.launch

class BooksViewModel(private val getBooksUseCase: GetBooksUseCase) : ViewModel() {

    var bookUiState by mutableStateOf(BooksUiState())
        private set

    fun getBooks() {
        viewModelScope.launch {
            val result = getBooksUseCase()
            result.onSuccess { books ->
                bookUiState = bookUiState.copy(books = books)
            }.onFailure { error ->
                Log.e("BooksViewModel", "$error")
                bookUiState = bookUiState.copy(error = error)
            }
            bookUiState = bookUiState.copy(isLoading = false)
        }
    }
}