package com.tapdoo.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.tapdoo.presentation.R
import com.tapdoo.presentation.navigation.BookDetailNavigation
import com.tapdoo.presentation.viewmodel.BookDetailViewModel
import com.tapdoo.ui.theme.spacing
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

    LaunchedEffect(viewModel) {
        viewModel.getBookDetail(bookDetail.bookId)
    }

    LaunchedEffect(uiState) {
        if (uiState.error != null) {
            snackBarHostState.showSnackbar("Unknown Error")
        }
    }

    BackHandler(onBack = onBackPress)

//    LoadingOverlay(isLoading = uiState.isLoading) {
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
//            if (uiState.bookDetail != null) {
        BookDetailContent(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
            contentPadding = contentPadding,
            bookDetail = bookDetail,
        )
//            }
    }
//    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun BookDetailContent(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contentPadding: PaddingValues,
    bookDetail: BookDetailNavigation,
) {

    val imageKey = stringResource(R.string.image_key, bookDetail.bookId)
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
                    model = bookDetail.bookUrl,
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
        }
    }
}