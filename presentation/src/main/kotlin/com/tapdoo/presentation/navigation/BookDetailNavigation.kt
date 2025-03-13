package com.tapdoo.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tapdoo.presentation.screen.BookDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailNavigation(
    val bookId: Int,
    val bookUrl: String,
    val bookTitle: String,
    val bookAuthor: String,
    val price: String,
)

fun NavController.navigateToBookDetail(bookDetailNavigation: BookDetailNavigation) {
    navigate(bookDetailNavigation)
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.bookDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    onBackPress: () -> Unit,
) {
    composable<BookDetailNavigation> { backStackEntry ->
        val bookDetail: BookDetailNavigation = backStackEntry.toRoute()
        BookDetailScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            bookDetail = bookDetail,
            onBackPress = onBackPress
        )
    }
}