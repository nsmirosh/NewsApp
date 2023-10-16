package nick.mirosh.newsapp.ui.favorite_articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.DomainState
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.ui.feed.FeedUIState
import javax.inject.Inject

@HiltViewModel
class FavoriteArticlesViewModel @Inject constructor(
    private val fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase
) : ViewModel() {
    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())
    val articles: Flow<List<Article>> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Idle)
    val uiState: StateFlow<FeedUIState> = _uiState

    init {
        viewModelScope.launch {
            when (val result = fetchFavoriteArticlesUsecase.invoke()) {
                is DomainState.Success -> {
                    _articles.emit(result.data)
                }

                is DomainState.Error -> {
                    Log.d("FavoriteArticlesViewModel", "Error")
                }

                else -> {
                    Log.d("FavoriteArticlesViewModel", "Something else happened")
                }
            }
        }
    }
}