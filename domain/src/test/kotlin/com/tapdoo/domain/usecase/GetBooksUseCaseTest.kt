package com.tapdoo.domain.usecase

import com.tapdoo.domain.model.Book
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

class GetBooksUseCaseTest : KoinTest {

    private lateinit var useCase: GetBooksUseCase
    private val mockBookApi: BookApi = mockk(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetBooksUseCase(mockBookApi)
    }

    @After
    fun tearDown() {
        stopKoin() // Stop Koin to free memory
        clearAllMocks()
    }

    @Test
    fun `invoke should return list of books on success`() = runTest {
        // Arrange
        val books = listOf(
            Book(
                id = 1,
                title = "Book1",
                author = "Author1",
                priceWithCurrency = "$10",
                isbn = "12345"
            ),
            Book(
                id = 2,
                title = "Book2",
                author = "Author2",
                priceWithCurrency = "$15",
                isbn = "67890"
            )
        )
        coEvery { mockBookApi.getBooks() } returns Result.success(books)

        // Act
        val result = useCase.invoke()

        // Assert
        assertEquals(Result.success(books), result)
        coVerify(exactly = 1) { mockBookApi.getBooks() }
    }

    @Test
    fun `invoke should return failure when API fails`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        coEvery { mockBookApi.getBooks() } returns Result.failure(Exception(errorMessage))

        // Act
        val result = useCase.invoke()

        // Assert
        assert(result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { mockBookApi.getBooks() }
    }
}