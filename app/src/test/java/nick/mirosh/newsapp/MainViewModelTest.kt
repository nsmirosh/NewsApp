package nick.mirosh.newsapp

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import nick.mirosh.newsapp.domain.ErrorType
import nick.mirosh.newsapp.domain.Resource
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.domain.usecase.articles.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.usecase.articles.LikeArticleUsecase
import nick.mirosh.newsapp.ui.MainViewModel
import nick.mirosh.newsapp.ui.feed.FeedUIState
import org.junit.Assert.assertEquals
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

    @Test
    fun onLikeClick_withValidArticle_updatesArticlesList() = runTest {
        //Arrange
        val fetchArticlesResult = Resource.Success(listOf(notLikedArticle))
        `when`(fetchArticlesUsecase.invoke()).thenReturn(fetchArticlesResult)

        val result = Resource.Success(likedArticle)
        `when`(likeArticleUsecase.invoke(notLikedArticle)).thenReturn(result)
        val expected = listOf(likedArticle)

        //Act
        val viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)

        //allow for running of animations and other stuff
        delay(2500)
        viewModel.onLikeClick(notLikedArticle)
        advanceUntilIdle()

        //Assert
        assertEquals(
            expected,
            viewModel.articles.toList()
        )
    }


    @Test
    fun onLikeClick_withFailure_doesNotUpdateArticleList() = runTest {
        //Arrange
        val fetchArticlesResult = Resource.Success(listOf(notLikedArticle))
        `when`(fetchArticlesUsecase.invoke()).thenReturn(fetchArticlesResult)

        val result = Resource.Error(ErrorType.General)
        `when`(likeArticleUsecase.invoke(notLikedArticle)).thenReturn(result)
        val expected = listOf(notLikedArticle)

        //Act
        val viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)

        //allow for running of animations and other stuff
        delay(2500)
        viewModel.onLikeClick(notLikedArticle)
        advanceUntilIdle()

        //Assert
        assertEquals(
            expected,
            viewModel.articles.toList()
        )
    }

    private val notLikedArticle = Article(
        author = "author",
        content = "content",
        description = "description",
        publishedAt = "publishedAt",
        title = "title",
        url = "url",
        urlToImage = "urlToImage",
        liked = false,
    )

    private val likedArticle = Article(
        author = "author",
        content = "content",
        description = "description",
        publishedAt = "publishedAt",
        title = "title",
        url = "url",
        urlToImage = "urlToImage",
        liked = true,
    )
}