package nick.mirosh.pokeapp.ui.favorite_articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nick.mirosh.pokeapp.data.repository.NewsRepository
import nick.mirosh.pokeapp.di.Cache
import javax.inject.Inject

@HiltViewModel
class FavoriteArticlesViewModel @Inject constructor(
    @Cache private val newsRepository: NewsRepository,
) : ViewModel() {
    val articles = newsRepository.articles

    init {
        viewModelScope.launch {
            newsRepository.getFavoriteArticles()
        }
    }
}