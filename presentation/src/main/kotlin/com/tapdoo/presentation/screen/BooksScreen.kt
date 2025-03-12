package com.tapdoo.presentation.screen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.tapdoo.domain.model.Book
import com.tapdoo.presentation.R
import com.tapdoo.presentation.viewmodel.BooksViewModel
import com.tapdoo.ui.components.LoadingOverlay
import com.tapdoo.ui.theme.BookAppTheme
import com.tapdoo.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun BookScreen(
    onBackPressed: () -> Unit,
    viewModel: BooksViewModel = koinViewModel()
) {

    val uiState = viewModel.bookUiState
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.getBooks()
    }

    LaunchedEffect(uiState) {
        if (uiState.error != null) {
            snackBarHostState.showSnackbar("Unknown Error")
        }
    }

    BackHandler(onBack = onBackPressed)

    LoadingOverlay(isLoading = uiState.isLoading) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { contentPadding ->
            if (uiState.books.isNotEmpty()) {
                BookContent(
                    books = uiState.books,
                    contentPadding = contentPadding
                )
            }
        }
    }
}

@Composable
private fun BookContent(
    books: List<Book>,
    contentPadding: PaddingValues,
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
            BookInfoSection()
        }

        items(items = books, key = { it.id }) { book ->
            BookCard(
                book = book,
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
private fun BookInfoSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        Text(
            text = stringResource(R.string.hello_name),
            modifier = Modifier.wrapContentWidth(),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            )
        )
        Text(
            text = stringResource(R.string.book_description),
            modifier = Modifier.wrapContentWidth(),
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
        )
    }
}

@Composable
private fun BookCard(book: Book, modifier: Modifier = Modifier) {
    val bookUrl = "https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg"
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = bookUrl,
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Novel by ${book.author}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Text(
                    text = "${book.price} ${book.currencySymbol}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(
    name = "BookCard",
    group = "Components",
    showBackground = true,
)
@Preview(
    name = "BookCard",
    group = "Components",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun BookCardPreview() {
    val bookState = Book(
        id = 1,
        title = "My book",
        price = 100,
        author = "My Author",
        currencySymbol = "Euro",
        isbn = "1234567890"
    )
    BookAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 4.dp,
        ) {
            BookCard(book = bookState)
        }
    }
}