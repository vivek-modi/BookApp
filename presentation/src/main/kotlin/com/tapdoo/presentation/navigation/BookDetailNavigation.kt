package com.tapdoo.presentation.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailNavigation(val bookId: Int)

fun NavController.navigateToBookDetail(bookId: Int) {
    navigate(BookDetailNavigation(bookId = bookId))
}


fun NavGraphBuilder.bookDetailScreen() {
    composable<BookDetailNavigation> {
        Text("Hello world")
    }
}