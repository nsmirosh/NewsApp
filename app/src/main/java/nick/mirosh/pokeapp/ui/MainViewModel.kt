package nick.mirosh.pokeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.pokeapp.data.repository.NewsRepository
import nick.mirosh.pokeapp.database.AppDatabase
import nick.mirosh.pokeapp.entity.ArticleDTO
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val appDatabase: AppDatabase
) : ViewModel() {

    private val _articles = MutableStateFlow<List<ArticleDTO>>(emptyList())

    val articles: StateFlow<List<ArticleDTO>> = _articles.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
/*            val newsList = newsRepository.getNewsList(Dispatchers.IO)
            appDatabase.articleDao().insertAll(*newsList.toTypedArray())
            _articles.value = newsList*/

            _articles.value = appDatabase.articleDao().getAll()
        }
    }
}