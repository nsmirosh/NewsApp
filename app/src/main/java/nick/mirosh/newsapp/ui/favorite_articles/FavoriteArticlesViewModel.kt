package nick.mirosh.newsapp.ui.favorite_articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.data.DataState
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.di.Cache
import nick.mirosh.newsapp.entity.Article
import javax.inject.Inject

@HiltViewModel
class FavoriteArticlesViewModel @Inject constructor(
    @Cache private val newsRepository: NewsRepository,
) : ViewModel() {
    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())
    val articles: Flow<List<Article>> = _articles

    init {
        viewModelScope.launch {
            Log.d(
                "FavoriteArticlesViewModel",
                "@Cache newsRepository.hashCode = ${newsRepository.hashCode()}"
            )
            newsRepository.getFavoriteArticles().collect { data ->
                when (data) {
                    is DataState.Success -> {
                        _articles.emit(data.data)
                    }

                    is DataState.Error -> {

                    }
                    else -> {

                    }
                }
            }
        }
    }
}