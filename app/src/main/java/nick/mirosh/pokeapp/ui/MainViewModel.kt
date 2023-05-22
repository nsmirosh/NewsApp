package nick.mirosh.pokeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.pokeapp.data.repository.NewsRemoteDataSource
import nick.mirosh.pokeapp.data.repository.NewsRepositoryImpl
import nick.mirosh.pokeapp.entity.ArticleDTO

class MainViewModel : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<ArticleDTO>>(emptyList())

    val pokemonList: StateFlow<List<ArticleDTO>> = _pokemonList.asStateFlow()

    init {
        viewModelScope.launch {
            val pokemonRepository = NewsRepositoryImpl(NewsRemoteDataSource())
            _pokemonList.value = pokemonRepository.getNewsList(Dispatchers.IO)
        }
    }

}