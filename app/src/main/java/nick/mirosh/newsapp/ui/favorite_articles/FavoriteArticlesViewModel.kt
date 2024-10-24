package nick.mirosh.newsapp.ui.favorite_articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
import nick.mirosh.newsapp.utils.MyLogger

class FavoriteArticlesViewModel (
    private val fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteArticlesUIState>(FavoriteArticlesUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            when (val result = fetchFavoriteArticlesUsecase()) {
                is Result.Success ->
                    _uiState.value = if (result.data.isEmpty())
                        FavoriteArticlesUIState.FavoriteArticlesEmpty
                    else
                        FavoriteArticlesUIState.FavoriteArticles(result.data)

                is Result.Error -> {
                    _uiState.value = FavoriteArticlesUIState.Failed
                    MyLogger.e("FavoriteArticlesViewModel", "Error = ${result.error}")
                }
            }
        }
    }
}