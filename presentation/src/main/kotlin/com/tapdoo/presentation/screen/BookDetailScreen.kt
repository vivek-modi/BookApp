package com.tapdoo.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import com.tapdoo.presentation.R
import com.tapdoo.presentation.components.AnimatedLoadingGradient
import com.tapdoo.presentation.navigation.BookDetailNavigation
import com.tapdoo.presentation.state.BookDetailUiState
import com.tapdoo.presentation.theme.spacing
import com.tapdoo.presentation.viewmodel.BookDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun BookDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    bookDetail: BookDetailNavigation,
    onBackPress: () -> Unit,
    viewModel: BookDetailViewModel = koinViewModel()
) {
    val uiState = viewModel.bookDetailUiState
    val snackBarHostState = remember { SnackbarHostState() }
    val errorMessage = stringResource(R.string.error)
    val retryMessage = stringResource(R.string.retry_label)

    LaunchedEffect(viewModel) {
        viewModel.getBookDetail(bookDetail.bookId)
    }

    LaunchedEffect(uiState) {
        if (uiState.error != null) {
            snackBarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = retryMessage,
                duration = SnackbarDuration.Short
            ).run {
                if (this == SnackbarResult.ActionPerformed) {
                    viewModel.getBookDetail(bookDetail.bookId)
                }
            }
        }
    }

    BackHandler(onBack = onBackPress)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { contentPadding ->
        BookDetailContent(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
            contentPadding = contentPadding,
            bookDetailNavigation = bookDetail,
            uiState = uiState
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun BookDetailContent(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contentPadding: PaddingValues,
    bookDetailNavigation: BookDetailNavigation,
    uiState: BookDetailUiState,
) {
    val titleKey = stringResource(R.string.title_key, bookDetailNavigation.bookId)
    val imageKey = stringResource(R.string.image_key, bookDetailNavigation.bookId)
    val novelKey = stringResource(R.string.novel_key, bookDetailNavigation.bookId)
    val priceKey = stringResource(R.string.price_key, bookDetailNavigation.bookId)

    with(sharedTransitionScope) {
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
                AsyncImage(
                    model = bookDetailNavigation.bookUrl,
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = imageKey),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f),
                    contentDescription = null,
                )
            }

            item {
                Text(
                    text = bookDetailNavigation.bookTitle,
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = titleKey),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center,
                )
            }

            item {
                Text(
                    text = stringResource(R.string.novel_by, bookDetailNavigation.bookAuthor),
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = novelKey),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    textAlign = TextAlign.Center,
                )
            }

            item {
                Text(
                    text = bookDetailNavigation.price,
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = priceKey),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center,
                )
            }

            item {
                Text(
                    text = stringResource(R.string.description),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }

            item {
                AnimatedVisibility(!uiState.isLoading) {
                    uiState.bookDetail?.let {
                        Text(
                            text = it.description,
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                    }
                }
                AnimatedVisibility(uiState.isLoading) {
                    AnimatedLoadingGradient()
                }
            }
        }
    }
}