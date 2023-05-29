package nick.mirosh.pokeapp.ui.favorite_articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.pokeapp.data.repository.NewsRepository
import nick.mirosh.pokeapp.di.Cache
import nick.mirosh.pokeapp.di.Universal
import nick.mirosh.pokeapp.entity.Article
import javax.inject.Inject

@HiltViewModel
class FavoriteArticlesViewModel @Inject constructor(
    @Cache private val newsRepository: NewsRepository,
) : ViewModel() {
    val articles = newsRepository.articles

    init {
        viewModelScope.launch {

            newsRepository.getNewsList()
        }
    }
}