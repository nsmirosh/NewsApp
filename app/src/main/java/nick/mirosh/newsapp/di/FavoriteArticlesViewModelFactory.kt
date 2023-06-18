package nick.mirosh.newsapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nick.mirosh.newsapp.data.repository.NewsRepository
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesViewModel

class FavoriteArticlesViewModelFactory(private val newsRepository: NewsRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return FavoriteArticlesViewModel(
                newsRepository = newsRepository
            ) as T
    }
}
