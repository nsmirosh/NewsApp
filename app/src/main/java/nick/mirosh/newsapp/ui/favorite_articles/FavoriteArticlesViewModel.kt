package nick.mirosh.newsapp.ui.favorite_articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.DomainState
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
import nick.mirosh.newsapp.entity.Article
import javax.inject.Inject

@HiltViewModel
class FavoriteArticlesViewModel @Inject constructor(
    private val fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase
) : ViewModel() {
    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())
    val articles: Flow<List<Article>> = _articles

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