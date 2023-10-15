package nick.mirosh.newsapp.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.DomainState
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
        viewModelScope.launch {
            _uiState.value = FeedUIState.Loading
            _uiState.value = when (val result = fetchArticlesUsecase()) {
                is DomainState.Success -> FeedUIState.Feed(result.data)
                is DomainState.Loading -> FeedUIState.Loading
                else -> FeedUIState.Failed
            }
        }
    }

    //https://stackoverflow.com/questions/74699081/jetpack-compose-lazy-column-all-items-recomposes-when-a-single-item-update
    fun onLikeClick(article: Article) {
        viewModelScope.launch {
            val parameters = article.copy(liked = !article.liked)
            when (val result = likeArticleUsecase(parameters)) {
                is DomainState.Success -> {
                    val updatedArticle = result.data
                    val index = articles.indexOfFirst { it.url == updatedArticle.url }
                    _articles[index] = updatedArticle
                }

                is DomainState.Error -> Log.e("MainViewModel", "Error: ${result.message}")
                else -> Log.d("MainViewModel", "Something else happened")
            }
        }
    }
}