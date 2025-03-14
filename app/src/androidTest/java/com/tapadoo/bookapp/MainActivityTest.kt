package com.tapadoo.bookapp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createComposeRule
import com.tapadoo.bookapp.navigation.BookNavHost
import com.tapdoo.presentation.theme.BookAppTheme
import com.tapdoo.presentation.theme.size
import org.junit.Rule
import org.junit.Test
import org.koin.compose.KoinContext

@ExperimentalMaterial3Api
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_main_screen_loads() {
        composeTestRule.setContent {
            BookAppTheme {
                KoinContext {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        tonalElevation = MaterialTheme.size.extraSmall,
                    ) {
                        BookNavHost()
                    }
                }
            }
        }
    }
}