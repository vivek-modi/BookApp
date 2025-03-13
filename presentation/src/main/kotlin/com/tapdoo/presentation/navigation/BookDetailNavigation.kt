package com.tapdoo.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tapdoo.presentation.screen.BookDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailNavigation(val bookId: Int)

fun NavController.navigateToBookDetail(bookId: Int) {
    navigate(BookDetailNavigation(bookId = bookId))
}


fun NavGraphBuilder.bookDetailScreen() {
    composable<BookDetailNavigation> { backStackEntry ->
        val bookDetail: BookDetailNavigation = backStackEntry.toRoute()
        BookDetailScreen(bookDetail.bookId)
    }
}