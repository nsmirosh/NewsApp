package nick.mirosh.newsapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val fetchArticlesUsecase: FetchArticlesUsecase,
    private val likeArticleUsecase: LikeArticleUsecase,
    private val networkConnectivityUseCase: NetworkConnectivityUseCase
) : ViewModel() {

    private val _articles = mutableStateListOf<Article>()
    val articles: List<Article> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Idle)
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
        _uiState.value = when (val result = fetchArticlesUsecase()) {
            is Result.Success -> {
                _articles.clear()
                _articles.addAll(result.data)
                FeedUIState.Feed(articles)
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