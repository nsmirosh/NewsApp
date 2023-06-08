package nick.mirosh.newsapp.ui.favorite_articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.data.repository.NewsRepository

class FavoriteArticlesViewModel constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {
    val articles = newsRepository.articles

    init {
        viewModelScope.launch {
            Log.d("FavoriteArticlesViewModel", "@Cache newsRepository.hashCode = ${newsRepository.hashCode()}")
            newsRepository.getFavoriteArticles()
        }
    }
}