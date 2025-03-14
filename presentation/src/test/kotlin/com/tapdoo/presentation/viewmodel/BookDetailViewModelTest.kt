package com.tapdoo.presentation.viewmodel

import android.util.Log
import com.tapdoo.domain.model.BookDetail
import com.tapdoo.domain.usecase.GetBookDetailUseCase
import com.tapdoo.presentation.coroutine.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookDetailViewModelTest {

    private lateinit var viewModel: BookDetailViewModel
    private val mockGetBookDetailUseCase: GetBookDetailUseCase = mockk()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        viewModel = BookDetailViewModel(mockGetBookDetailUseCase)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getBookDetail should update UI state on success`() = runTest {
        // Arrange
        val bookId = 1
        val bookDetail = BookDetail(
            id = bookId,
            title = "Test Book",
            author = "Test Author",
            description = "A sample book description",
            priceWithCurrency = "100$",
            isbn = "213"
        )
        coEvery { mockGetBookDetailUseCase(bookId) } returns Result.success(bookDetail)

        // Act
        viewModel.getBookDetail(bookId)

        // Assert
        assertEquals(true, viewModel.bookDetailUiState.isLoading) // Initially loading
        advanceUntilIdle() // Ensure coroutine execution completes
        assertEquals(bookDetail, viewModel.bookDetailUiState.bookDetail)
        assertEquals(false, viewModel.bookDetailUiState.isLoading)
        assertEquals(null, viewModel.bookDetailUiState.error)
    }

    @Test
    fun `getBookDetail should update UI state on failure`() = runTest {
        // Arrange
        val bookId = 2
        val errorMessage = "Network error"
        coEvery { mockGetBookDetailUseCase(bookId) } returns Result.failure(Exception(errorMessage))

        // Act
        viewModel.getBookDetail(bookId)

        // Assert
        assertEquals(true, viewModel.bookDetailUiState.isLoading) // Initially loading
        advanceUntilIdle() // Ensure coroutine execution completes
        assertEquals(null, viewModel.bookDetailUiState.bookDetail)
        assertEquals(errorMessage, viewModel.bookDetailUiState.error)
        assertEquals(false, viewModel.bookDetailUiState.isLoading)
    }
}