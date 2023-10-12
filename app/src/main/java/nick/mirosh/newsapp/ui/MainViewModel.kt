package nick.mirosh.newsapp.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.DomainState
import nick.mirosh.newsapp.domain.usecase.articles.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.usecase.articles.LikeArticleUsecase
import nick.mirosh.newsapp.entity.Article
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchArticlesUsecase: FetchArticlesUsecase,
    private val likeArticleUsecase: LikeArticleUsecase,
) : ViewModel() {

    private val _articles = mutableStateListOf<Article>()
    val articles = _articles

    init {
        viewModelScope.launch {
            when (val result = fetchArticlesUsecase()) {
                is DomainState.Success -> {
                    _articles.addAll(result.data)
                    Log.d("MainViewModel", "Success")
                }

                is DomainState.Error -> {
                    Log.d("MainViewModel", "Error")
                }

                else -> {
                    Log.d("MainViewModel", "Something else happened")
                }
            }
        }
    }

    //https://stackoverflow.com/questions/74699081/jetpack-compose-lazy-column-all-items-recomposes-when-a-single-item-update
    fun onLikeClick(article: Article) {
        viewModelScope.launch {
            val parameters = article.copy(liked = !article.liked)
            when (val result = likeArticleUsecase(parameters)) {
                is DomainState.Success -> {
                    val updatedArticle = result.data
                    val index = articles.indexOfFirst { it.url == updatedArticle.url }
                    _articles[index] = updatedArticle
                }

                is DomainState.Error -> Log.e("MainViewModel", "Error: ${result.message}")
                else -> Log.d("MainViewModel", "Something else happened")
            }
        }
    }
}