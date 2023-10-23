package nick.mirosh.newsapp.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Resource
import nick.mirosh.newsapp.domain.usecase.articles.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.usecase.articles.LikeArticleUsecase
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.ui.feed.FeedUIState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchArticlesUsecase: FetchArticlesUsecase,
    private val likeArticleUsecase: LikeArticleUsecase,
) : ViewModel() {

    private val _articles = mutableStateListOf<Article>()
    val articles: List<Article> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Idle)
    val uiState: StateFlow<FeedUIState> = _uiState

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            _uiState.value = FeedUIState.Loading
            //Just adding this for now to demonstrate the smiley loading animation
            delay(2000)
            _uiState.value = when (val result = fetchArticlesUsecase()) {
                is Resource.Success -> {
                    _articles.addAll(result.data)
                    FeedUIState.Feed(_articles)
                }

                else -> FeedUIState.Failed
            }
        }
    }

    //https://stackoverflow.com/questions/74699081/jetpack-compose-lazy-column-all-items-recomposes-when-a-single-item-update
    fun onLikeClick(article: Article) {
        viewModelScope.launch {
            val parameters = article.copy(liked = !article.liked)
            when (val result = likeArticleUsecase(parameters)) {
                is Resource.Success -> {
                    val index = articles.indexOfFirst { it.url == result.data.url }
                    _articles[index] = result.data
                }

                is Resource.Error -> Log.e("MainViewModel", "Error: ${result.error}")
            }
        }
    }
}