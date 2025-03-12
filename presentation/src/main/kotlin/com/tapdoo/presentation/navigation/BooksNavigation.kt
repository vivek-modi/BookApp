package com.tapdoo.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tapdoo.presentation.screen.BookScreen
import kotlinx.serialization.Serializable

@Serializable
object BooksNavigation

fun NavGraphBuilder.booksScreen(
    onBackPressed: () -> Unit,
    onNavigateToBookDetail: (Int) -> Unit,
) {
    composable<BooksNavigation> {
        BookScreen(
            onBackPressed = onBackPressed,
            onNavigateToBookDetail = onNavigateToBookDetail
        )
    }
}