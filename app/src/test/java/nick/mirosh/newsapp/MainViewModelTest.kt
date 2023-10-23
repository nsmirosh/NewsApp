package nick.mirosh.newsapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nick.mirosh.newsapp.domain.ErrorType
import nick.mirosh.newsapp.domain.Resource
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.domain.usecase.articles.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.usecase.articles.LikeArticleUsecase
import nick.mirosh.newsapp.ui.MainViewModel
import nick.mirosh.newsapp.ui.feed.FeedUIState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var likeArticleUsecase: LikeArticleUsecase
    private lateinit var fetchArticlesUsecase: FetchArticlesUsecase

    @Before
    fun setUp() {
        fetchArticlesUsecase = mock(FetchArticlesUsecase::class.java)
        likeArticleUsecase = mock(LikeArticleUsecase::class.java)
        viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)
    }


    @Test
    fun init_getsTheArticleListSuccessfully_sendsFeedEventToUI() = runTest {
        //Arrange
        val articles = listOf<Article>()
        val result = Resource.Success(articles)
        `when`(fetchArticlesUsecase.invoke()).thenReturn(result)
        val expected = FeedUIState.Feed(articles)

        //Act
        val viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)

        //Assert
        val actual = viewModel.uiState.first()
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun init_getsTheArticleListWithFailure_sendsFailedEventToUI() = runTest {
        //Arrange
        val result = Resource.Error(ErrorType.General)
        `when`(fetchArticlesUsecase.invoke()).thenReturn(result)
        val expected = FeedUIState.Feed(listOf())

        //Act
        val viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)


        //Assert
        val actual = viewModel.uiState.first()

        assertEquals(
            expected,
            actual
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun init_getsTheArticleListWithFailure_sends3FailedEventToUI() = runTest{

        // Arrange
        val result = Resource.Error(ErrorType.General)
        `when`(fetchArticlesUsecase.invoke()).thenReturn(result)

        val emissions = mutableListOf<FeedUIState>()


        var viewModel: MainViewModel? = null

        // Launch a coroutine to collect emissions
        val job = launch {
            viewModel?.uiState?.collect {
                emissions.add(it)
            }
        }

        // Act
        viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)

        // Allow coroutines to execute
        advanceUntilIdle()

        // Assert
        assertTrue(emissions[0] is FeedUIState.Idle)
        assertTrue(emissions[1] is FeedUIState.Loading)

        // Clean up
        job.cancel()
        Dispatchers.resetMain() // Reset main dispatcher to the original dispatcher

    }
}