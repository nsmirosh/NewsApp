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

    private val _uiState = MutableStateFlow<FavoriteArticlesUIState>(FavoriteArticlesUIState.Idle)
    val uiState: StateFlow<FavoriteArticlesUIState> = _uiState

    init {
        viewModelScope.launch {
            when (val result = fetchFavoriteArticlesUsecase.invoke()) {
                is DomainState.Success ->
                    _uiState.emit(FavoriteArticlesUIState.FavoriteArticles(result.data))

                is DomainState.Error ->
                    Log.e("FavoriteArticlesViewModel", "Error = ${result.message}")

                else -> {
                    Log.d("FavoriteArticlesViewModel", "Something else happened")
                }
            }
        }
    }
}