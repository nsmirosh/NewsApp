package nick.mirosh.newsapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsapp.domain.network.usecase.NetworkConnectivityUseCase
import nick.mirosh.newsapp.ui.feed.FeedUIState
import nick.mirosh.newsapp.utils.MyLogger

class FeedViewModel(
    private val fetchArticlesUsecase: FetchArticlesUsecase,
    private val likeArticleUsecase: LikeArticleUsecase,
    private val networkConnectivityUseCase: NetworkConnectivityUseCase
) : ViewModel() {

    private val _articles = mutableStateListOf<Article>()
    val articles: List<Article> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
    val uiState: StateFlow<FeedUIState> = _uiState

    init {
        viewModelScope.launch {
            networkConnectivityUseCase().collect { isNetworkAvailable ->
                if (isNetworkAvailable) {
                    fetchArticles()
                } else {
                    _uiState.value = FeedUIState.NoNetworkConnection
                }
            }
        }
    }

    private suspend fun fetchArticles() {
        _uiState.value = FeedUIState.Loading
        _uiState.value = when (val result = fetchArticlesUsecase("us")) {
            is Result.Success -> {
                _articles.clear()
                _articles.addAll(result.data)
                if (result.data.isNotEmpty()) FeedUIState.Feed(articles) else FeedUIState.Empty
            }

            is Result.Error -> FeedUIState.Failed
        }
    }

    //https://stackoverflow.com/questions/74699081/jetpack-compose-lazy-column-all-items-recomposes-when-a-single-item-update
    fun onLikeClick(article: Article) {
        viewModelScope.launch {
            when (val result = likeArticleUsecase(article)) {
                is Result.Success -> {
                    val index = articles.indexOfFirst { it.url == result.data.url }
                    _articles[index] = result.data
                }

                is Result.Error -> {
                    MyLogger.e("MainViewModel", "Error: ${result.error}")
                }
            }
        }
    }
}