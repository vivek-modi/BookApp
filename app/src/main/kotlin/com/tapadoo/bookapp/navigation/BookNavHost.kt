package com.tapadoo.bookapp.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tapdoo.presentation.navigation.BooksNavigation
import com.tapdoo.presentation.navigation.booksScreen

@Composable
fun BookNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    val currentActivity = LocalActivity.current
    NavHost(
        navController = navHostController,
        startDestination = BooksNavigation
    ) {
        booksScreen(
            onBackPressed = {
                currentActivity?.finish()
            }
        )
    }
}