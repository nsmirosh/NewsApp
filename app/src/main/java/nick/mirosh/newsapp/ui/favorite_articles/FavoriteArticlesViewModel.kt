package nick.mirosh.newsapp.ui.favorite_articles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.di.Cache
import javax.inject.Inject

@HiltViewModel
class FavoriteArticlesViewModel @Inject constructor(
    @Cache private val newsRepository: NewsRepository,
) : ViewModel() {
    val articles = newsRepository.articles

    init {
        Log.d(
            "FavoriteArticlesViewModel",
            "@Cache newsRepository.hashCode = ${newsRepository.hashCode()}"
        )
        viewModelScope.launch {
            newsRepository.getFavoriteArticles()
        }
    }
}