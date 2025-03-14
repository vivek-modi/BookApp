package com.tapdoo.domain.usecase

import com.tapdoo.domain.model.BookDetail
import com.tapdoo.domain.repository.BookApi
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class GetBookDetailUseCaseTest : KoinTest {

    private lateinit var useCase: GetBookDetailUseCase
    private val mockBookApi: BookApi = mockk(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetBookDetailUseCase(mockBookApi)
    }

    @After
    fun tearDown() {
        stopKoin() // Stop Koin to free memory
        clearAllMocks()
    }

    @Test
    fun `invoke should return book detail on success`() = runTest {
        // Arrange
        val bookId = 1
        val bookDetail = BookDetail(
            id = bookId,
            title = "Test Book",
            author = "Test Author",
            description = "A sample book description",
            priceWithCurrency = "$10.99",
            isbn = "USD"
        )
        coEvery { mockBookApi.getBookDetail(bookId) } returns Result.success(bookDetail)

        // Act
        val result = useCase.invoke(bookId)

        // Assert
        assertEquals(Result.success(bookDetail), result)
        coVerify(exactly = 1) { mockBookApi.getBookDetail(bookId) }
    }

    @Test
    fun `invoke should return failure when API fails`() = runTest {
        // Arrange
        val bookId = 2
        val errorMessage = "Network error"
        coEvery { mockBookApi.getBookDetail(bookId) } returns Result.failure(Exception(errorMessage))

        // Act
        val result = useCase.invoke(bookId)

        // Assert
        assert(result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { mockBookApi.getBookDetail(bookId) }
    }
}