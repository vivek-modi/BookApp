package com.tapdoo.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tapdoo.presentation.screen.BookDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailNavigation(val bookId: Int, val bookUrl: String)

fun NavController.navigateToBookDetail(bookId: Int, bookIsbn: String) {
    navigate(BookDetailNavigation(bookId = bookId, bookUrl = bookIsbn))
}

fun NavGraphBuilder.bookDetailScreen(onBackPress: () -> Unit) {
    composable<BookDetailNavigation> { backStackEntry ->
        val bookDetail: BookDetailNavigation = backStackEntry.toRoute()
        BookDetailScreen(bookDetail, onBackPress)
    }
}