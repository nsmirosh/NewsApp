package nick.mirosh.newsapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.data.DataState
import nick.mirosh.newsapp.data.repository.NewsRepository
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

    fun getUpdatedList(updatedArticle: Article): List<Article> {
        val currentList = _articles.value.toMutableList()
        val index = currentList.indexOfFirst { it.url == updatedArticle.url }
        if (index != -1) {
            currentList[index] = updatedArticle
        }
        return currentList
    }

    fun onLikeClick(article: Article) {
        viewModelScope.launch {
            newsRepository.updateArticle(article.copy(liked = !article.liked)).collect {
                when (it) {
                    is DataState.Success -> {
                        _articles.value = getUpdatedList(it.data)
                    }
                    is DataState.Error -> Log.e("MainViewModel", "Error: ${it.message}")
                    else -> Log.d("MainViewModel", "Something else happened")
                }
            }
        }
    }
}