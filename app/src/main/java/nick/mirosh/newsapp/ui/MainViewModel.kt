package nick.mirosh.newsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.data.DataState
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.di.DefaultDispatcher
import nick.mirosh.newsapp.di.IoDispatcher
import nick.mirosh.newsapp.di.Universal
import nick.mirosh.newsapp.entity.Article
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @Universal private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())
    val articles: Flow<List<Article>> = _articles

    init {
        viewModelScope.launch {
            newsRepository.refreshNews().collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _articles.emit(state.data)
                    }
                    is DataState.Error -> Log.e("MainViewModel", "Error: ${state.message}")
                    else -> Log.d("MainViewModel", "Something else happened")
                }
            }
        }
    }

    fun onLikeClick(article: Article) {
        viewModelScope.launch {
            newsRepository.updateArticle(article.copy(liked = !article.liked))

        }
    }
}