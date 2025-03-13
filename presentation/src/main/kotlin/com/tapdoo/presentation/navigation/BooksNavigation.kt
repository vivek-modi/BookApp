package com.tapdoo.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tapdoo.presentation.screen.BookScreen
import kotlinx.serialization.Serializable

@Serializable
object BooksNavigation

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.booksScreen(
    sharedTransitionScope: SharedTransitionScope,
    onBackPressed: () -> Unit,
    onNavigateToBookDetail: (BookDetailNavigation) -> Unit,
) {
    composable<BooksNavigation> {
        BookScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onBackPressed = onBackPressed,
            onNavigateToBookDetail = onNavigateToBookDetail
        )
    }
}