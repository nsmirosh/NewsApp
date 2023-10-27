package nick.mirosh.newsapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
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

        //Act
        val viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)
        val emissions = viewModel.uiState.take(4).toList()

        //Assert
        assertEquals(FeedUIState.Idle, emissions[0])
        assertEquals(FeedUIState.Loading, emissions[1])
        assertEquals(FeedUIState.Idle, emissions[2])
        assertEquals(FeedUIState.Feed(articles), emissions[3])
    }

    @Test
    fun init_getsTheArticleListWithFailure_sendsFailedEventToUI() = runTest {
        //Arrange
        val result = Resource.Error(ErrorType.General)
        `when`(fetchArticlesUsecase.invoke()).thenReturn(result)
        val expected = FeedUIState.Failed

        //Act
        val viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)

        //Assert
        val emissions = viewModel.uiState.take(4).toList()
        assertEquals(
            expected,
            emissions[3]
        )
    }
}