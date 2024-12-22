package nick.mirosh.newsapp

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
import nick.mirosh.newsapp.helpers.MainDispatcherRule
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesUIState
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class FavoriteArticlesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase =
        mockk<FetchFavoriteArticlesUsecase>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun init_withEmptyArticleResponse_sends_FavoriteArticlesEmptyEvent_toTheUI() = runTest(
        StandardTestDispatcher()
    ) {

        //Arrange
        coEvery { fetchFavoriteArticlesUsecase.invoke() } returns flowOf(Result.Success(listOf()))

        //Act
        val viewModel = FavoriteArticlesViewModel(fetchFavoriteArticlesUsecase)

        val emissions = mutableListOf<FavoriteArticlesUIState>()
        val job = launch {
            viewModel.uiState.take(2).toList(emissions)
        }
        advanceUntilIdle()

        job.cancel()

        //Assert
        assertEquals(FavoriteArticlesUIState.Loading, emissions[0])
        assertEquals(FavoriteArticlesUIState.FavoriteArticlesEmpty, emissions[1])
    }
//
//    @Test
//    fun init_withEmptyArticleResponse_sends_FavoriteArticlesEvent_toTheUI() = runTest {
//
//        //Arrange
//        val result = Result.Success(listOf(likedArticle))
//        `when`(fetchFavoriteArticlesUsecase.invoke()).thenReturn(result)
//
//        //Act
//        val viewModel = FavoriteArticlesViewModel(fetchFavoriteArticlesUsecase)
//
//        val emissions = viewModel.uiState.take(2).toList()
//
//        //Assert
//        assertEquals(FavoriteArticlesUIState.Idle, emissions[0])
//        assertEquals(FavoriteArticlesUIState.FavoriteArticles(listOf(likedArticle)), emissions[1])
//    }
//
//    @Test
//    fun init_withEmptyArticleResponse_sends_Error_toTheUI() = runTest {
//
//        //Arrange
//        val result = Result.Error(ErrorType.General)
//        `when`(fetchFavoriteArticlesUsecase.invoke()).thenReturn(result)
//
//        //Act
//        val viewModel = FavoriteArticlesViewModel(fetchFavoriteArticlesUsecase)
//
//        val emissions = viewModel.uiState.take(2).toList()
//
//        //Assert
//        assertEquals(FavoriteArticlesUIState.Idle, emissions[0])
//        assertEquals(FavoriteArticlesUIState.Failed, emissions[1])
//    }
}