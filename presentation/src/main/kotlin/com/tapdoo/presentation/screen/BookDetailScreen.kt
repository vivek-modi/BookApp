package com.tapdoo.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.tapdoo.presentation.navigation.BookDetailNavigation
import com.tapdoo.presentation.viewmodel.BookDetailViewModel
import com.tapdoo.ui.components.LoadingOverlay
import com.tapdoo.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun BookDetailScreen(
    bookDetail: BookDetailNavigation,
    viewModel: BookDetailViewModel = koinViewModel()
) {
    val bookUrl = "https://covers.openlibrary.org/b/isbn/${bookDetail.bookIsbn}-L.jpg"
    val uiState = viewModel.bookDetailUiState
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.getBookDetail(bookDetail.bookId)
    }

    LaunchedEffect(uiState) {
        if (uiState.error != null) {
            snackBarHostState.showSnackbar("Unknown Error")
        }
    }

    LoadingOverlay(isLoading = uiState.isLoading) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { contentPadding ->
            if (uiState.bookDetail != null) {
                BookDetailContent(
                    contentPadding = contentPadding,
                    bookUrl = bookUrl,
                )
            }
        }
    }
}

@Composable
private fun BookDetailContent(
    contentPadding: PaddingValues,
    bookUrl: String,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(contentPadding)
            .imePadding()
            .padding(
                vertical = MaterialTheme.spacing.medium,
                horizontal = MaterialTheme.spacing.medium
            ),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
    ) {

        item {
            Spacer(Modifier.height(MaterialTheme.spacing.extraLarge))
        }

        item {
            AsyncImage(
                model = bookUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f),
                contentDescription = null,
            )
        }
    }
}