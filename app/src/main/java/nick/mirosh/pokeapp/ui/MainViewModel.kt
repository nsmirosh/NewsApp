package nick.mirosh.pokeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.pokeapp.data.repository.NewsRemoteDataSource
import nick.mirosh.pokeapp.data.repository.NewsRepository
import nick.mirosh.pokeapp.data.repository.NewsRepositoryImpl
import nick.mirosh.pokeapp.entity.ArticleDTO
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<ArticleDTO>>(emptyList())

    val pokemonList: StateFlow<List<ArticleDTO>> = _pokemonList.asStateFlow()

    init {
        viewModelScope.launch {
            _pokemonList.value = newsRepository.getNewsList(Dispatchers.IO)
        }
    }

}