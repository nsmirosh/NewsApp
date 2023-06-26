package nick.mirosh.newsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.di.Universal
import nick.mirosh.newsapp.entity.Article
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @Universal private val newsRepository: NewsRepository,
) : ViewModel() {
    val articles = newsRepository.articles

    init {
        Log.d(
            "MainViewModel",
            "@Universal newsRepository.hashCode = ${newsRepository.hashCode()}"
        )
        viewModelScope.launch {
            newsRepository.refreshNews()
        }
    }

    fun onLikeClick(article: Article) {
        viewModelScope.launch {
            newsRepository.updateArticle(article.copy(liked = !article.liked))
        }
    }
}