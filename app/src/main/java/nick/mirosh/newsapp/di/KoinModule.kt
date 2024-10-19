package nick.mirosh.newsapp.di

import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsapp.domain.network.usecase.NetworkConnectivityUseCase
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
import nick.mirosh.newsapp.ui.FeedViewModel
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    factory { FetchArticlesUsecase(get(), get(named(IO))) }
    factory { LikeArticleUsecase(get(), get(named(IO))) }
    factory { FetchFavoriteArticlesUsecase(get(), get(named(IO))) }
    factoryOf(::NetworkConnectivityUseCase)

    viewModelOf(::FeedViewModel)
    viewModelOf(::FavoriteArticlesViewModel)
}
