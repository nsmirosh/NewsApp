package nick.mirosh.newsapp

import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import nick.mirosh.newsapp.domain.ErrorType
import nick.mirosh.newsapp.domain.Resource
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
import nick.mirosh.newsapp.ui.MainViewModel
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesUIState
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesViewModel
import nick.mirosh.newsapp.ui.feed.FeedUIState
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class FavoriteArticlesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase

    @Before
    fun setUp() {
        fetchFavoriteArticlesUsecase = Mockito.mock(FetchFavoriteArticlesUsecase::class.java)
    }


    @Test
    fun init_withEmptyArticleResponse_sends_FavoriteArticlesEmptyEvent_toTheUI() = runTest {

        //Arrange
        val result = Resource.Success(listOf<Article>())
        `when`(fetchFavoriteArticlesUsecase.invoke()).thenReturn(result)

        //Act
        val viewModel = FavoriteArticlesViewModel(fetchFavoriteArticlesUsecase)

        val emissions = viewModel.uiState.take(2).toList()

        //Assert
        assertEquals(FavoriteArticlesUIState.Idle, emissions[0])
        assertEquals(FavoriteArticlesUIState.FavoriteArticlesEmpty, emissions[1])
    }
}