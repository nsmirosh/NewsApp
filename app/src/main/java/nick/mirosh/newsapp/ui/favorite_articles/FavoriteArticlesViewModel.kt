package nick.mirosh.newsapp.ui.favorite_articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Resource
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
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
                is Resource.Success ->
                    _uiState.emit(FavoriteArticlesUIState.FavoriteArticles(result.data))

                is Resource.Error ->
                    Log.e("FavoriteArticlesViewModel", "Error = ${result.error}")

                else -> {
                    Log.d("FavoriteArticlesViewModel", "Something else happened")
                }
            }
        }
    }
}