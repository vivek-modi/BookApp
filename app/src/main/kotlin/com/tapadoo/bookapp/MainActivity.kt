package com.tapadoo.bookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.tapadoo.bookapp.navigation.BookNavHost
import com.tapdoo.presentation.theme.BookAppTheme
import com.tapdoo.presentation.theme.size
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
