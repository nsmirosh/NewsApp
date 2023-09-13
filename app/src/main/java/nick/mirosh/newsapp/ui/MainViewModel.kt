package nick.mirosh.newsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.entity.Article

class MainViewModel constructor(
     private val newsRepository: NewsRepository,
) : ViewModel() {
    val articles = newsRepository.articles

    init {
        viewModelScope.launch {
            Log.d("MainViewModel", "@Universal newsRepository.hashCode = ${newsRepository.hashCode()}")
            newsRepository.refreshNews()
        }
    }

    fun onLikeClick(article: Article) {
        viewModelScope.launch {
            newsRepository.updateArticle(article.copy(liked = !article.liked))

        }
    }
}