package com.tapdoo.presentation.screen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tapdoo.domain.model.Book
import com.tapdoo.presentation.R
import com.tapdoo.presentation.components.LoadingOverlay
import com.tapdoo.presentation.navigation.BookDetailNavigation
import com.tapdoo.presentation.theme.BookAppTheme
import com.tapdoo.presentation.theme.size
import com.tapdoo.presentation.theme.spacing
import com.tapdoo.presentation.viewmodel.BooksViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun BookScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackPressed: () -> Unit,
    onNavigateToBookDetail: (BookDetailNavigation) -> Unit,
    viewModel: BooksViewModel = koinViewModel()
) {

    val uiState = viewModel.bookUiState
    var apiCalled by rememberSaveable { mutableStateOf(false) }
    var imageWidth by remember { mutableIntStateOf(0) }

    if (!apiCalled) {
        LaunchedEffect(true) {
            viewModel.getBooks()
            apiCalled = true
        }
    }

    BackHandler(onBack = onBackPressed)

    Scaffold { contentPadding ->
        Column {
            BookInfoSection(contentPadding)
            if (!uiState.isLoading && uiState.isError) {
                RetryView(viewModel::getBooks)
            }
            LoadingOverlay(isLoading = uiState.isLoading) {
                if (uiState.books.isNotEmpty()) {
                    BookContent(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        books = uiState.books,
                        imageWidth = imageWidth,
                        onImageWidthChange = { imageWidth = it },
                        onItemClick = onNavigateToBookDetail,
                    )
                }
            }
        }
    }
}

@Composable
private fun RetryView(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_retry),
            alignment = Alignment.Center,
            contentDescription = null,
        )
        Text(
            text = stringResource(R.string.error_generic_retry),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onRetryClick,
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraMedium),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Text(
                text = stringResource(R.string.retry_label),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun BookContent(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    books: List<Book>,
    imageWidth: Int,
    onImageWidthChange: (Int) -> Unit,
    onItemClick: (BookDetailNavigation) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.medium)
            .padding(bottom = MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
    ) {

        items(items = books, key = { it.id }) { book ->
            BookCard(
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
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
private fun BookInfoSection(contentPadding: PaddingValues) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(contentPadding)
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.medium
            ),
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun BookCard(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    book: Book,
    imageWidth: Int,
    onImageWidthChange: (Int) -> Unit,
    onItemClick: (BookDetailNavigation) -> Unit,
) {
    with(sharedTransitionScope) {
        val bookUrl = "https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg"
        val color = MaterialTheme.colorScheme.surfaceVariant
        val imageKey = stringResource(R.string.image_key, book.id)
        val titleKey = stringResource(R.string.title_key, book.id)
        val novelKey = stringResource(R.string.novel_key, book.id)
        Card(
            onClick = {
                onItemClick(
                    BookDetailNavigation(
                        bookId = book.id,
                        bookUrl = bookUrl,
                        bookTitle = book.title,
                        bookAuthor = book.author,
                        price = book.priceWithCurrency,
                    )
                )
            },
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(MaterialTheme.size.Card)
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
                AsyncImage(
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = imageKey),
                            animatedVisibilityScope = animatedContentScope
                        )
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
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(bookUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.title,
                        modifier = Modifier.sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = titleKey),
                            animatedVisibilityScope = animatedContentScope
                        ),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.novel_by, book.author),
                        modifier = Modifier.sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = novelKey),
                            animatedVisibilityScope = animatedContentScope
                        ),
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
            tonalElevation = MaterialTheme.size.extraSmall,
        ) {
            BookInfoSection(PaddingValues(MaterialTheme.spacing.medium))
        }
    }
}