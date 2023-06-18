package nick.mirosh.newsapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.ui.MainViewModel

class MainViewModelFactory(private val newsRepository: NewsRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(newsRepository = newsRepository) as T
    }
}
