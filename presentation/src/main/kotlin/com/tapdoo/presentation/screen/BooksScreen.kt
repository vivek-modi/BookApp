package com.tapdoo.presentation.screen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
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
    onNavigateToBookDetail: (Int) -> Unit,
    viewModel: BooksViewModel = koinViewModel()
) {

    val uiState = viewModel.bookUiState
    val snackBarHostState = remember { SnackbarHostState() }
    var imageWidth by remember { mutableIntStateOf(0) }

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
                    contentPadding = contentPadding,
                    imageWidth = imageWidth,
                    onImageWidthChange = {
                        imageWidth = it
                    },
                    onItemClick = onNavigateToBookDetail,
                )
            }
        }
    }
}

@Composable
private fun BookContent(
    books: List<Book>,
    contentPadding: PaddingValues,
    imageWidth: Int,
    onImageWidthChange: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
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

        item {
            Spacer(Modifier.height(MaterialTheme.spacing.extraLarge))
        }

        items(items = books, key = { it.id }) { book ->
            BookCard(
                book = book,
                modifier = Modifier.animateItem(),
                imageWidth = imageWidth,
                onImageWidthChange = onImageWidthChange,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
private fun BookInfoSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
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
        Image(
            painter = painterResource(R.drawable.avatar_svgrepo_com),
            alignment = Alignment.CenterEnd,
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.small)
                .weight(1f),
            contentDescription = null,
        )
    }
}

@Composable
private fun BookCard(
    modifier: Modifier = Modifier,
    book: Book,
    imageWidth: Int,
    onImageWidthChange: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    val bookUrl = "https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg"
    val color = MaterialTheme.colorScheme.surfaceVariant
    Card(
        onClick = {
            onItemClick(book.id)
        },
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val leftEdge = imageWidth / 2
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(leftEdge.toFloat(), 0f),
                        size = Size(size.width - leftEdge, size.height),
                        cornerRadius = CornerRadius(MaterialTheme.spacing.extraMedium.toPx())
                    )
                }
                .padding(
                    top = MaterialTheme.spacing.medium,
                    bottom = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .onGloballyPositioned {
                        onImageWidthChange(it.size.width)
                    }
                    .border(
                        BorderStroke(
                            MaterialTheme.spacing.extraSmall,
                            MaterialTheme.colorScheme.outline
                        ),
                        RectangleShape
                    ),
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
                    text = stringResource(R.string.novel_by, book.author),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Text(
                    text = book.priceWithCurrency,
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
        priceWithCurrency = "$10.00",
        author = "My Author",
        isbn = "1234567890"
    )
    BookAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 4.dp,
        ) {
            BookCard(
                book = bookState,
                imageWidth = 0,
                onImageWidthChange = {}
            ) {}
        }
    }
}

@Preview(
    name = "BookScreen",
    group = "Screens",
    showBackground = true,
)
@Preview(
    name = "BookScreen",
    group = "Screens",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun BookInfoSectionPreview() {
    BookAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 4.dp,
        ) {
            BookInfoSection()
        }
    }
}