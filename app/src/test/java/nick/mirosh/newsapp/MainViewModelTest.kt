package nick.mirosh.newsapp

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nick.mirosh.newsapp.domain.DomainState
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.domain.usecase.articles.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.usecase.articles.LikeArticleUsecase
import nick.mirosh.newsapp.ui.MainViewModel
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
    fun something() = runTest {
        //Arrange
        val articles = listOf<Article>()
        val result = DomainState.Success(articles)
        `when`(fetchArticlesUsecase.invoke()).thenReturn(result)

        //Act
        val viewModel = MainViewModel(fetchArticlesUsecase, likeArticleUsecase)

        //Assert
        viewModel.uiState.test {


        }
    }
}