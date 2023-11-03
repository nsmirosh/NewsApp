package nick.mirosh.newsapp.ui.favorite_articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Resource
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
import nick.mirosh.newsapp.utils.MyLogger
import javax.inject.Inject

@HiltViewModel
class FavoriteArticlesViewModel @Inject constructor(
    private val fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteArticlesUIState>(FavoriteArticlesUIState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            when (val result = fetchFavoriteArticlesUsecase()) {
                is Resource.Success ->
                    _uiState.value = if (result.data.isEmpty())
                        FavoriteArticlesUIState.FavoriteArticlesEmpty
                    else
                        FavoriteArticlesUIState.FavoriteArticles(result.data)

                is Resource.Error -> {
                    _uiState.value = FavoriteArticlesUIState.Failed
                    MyLogger.e("FavoriteArticlesViewModel", "Error = ${result.error}")
                }
            }
        }
    }
}