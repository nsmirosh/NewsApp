package nick.mirosh.newsapp.di

import androidx.room.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import nick.mirosh.newsapp.BuildConfig
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.data.database.DATABASE_NAME
import nick.mirosh.newsapp.data.networking.HeaderInterceptor
import nick.mirosh.newsapp.data.networking.NewsService
import nick.mirosh.newsapp.data.repository.NetworkConnectivityManager
import nick.mirosh.newsapp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsapp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsapp.domain.mapper.news.DTOtoDatabaseArticleMapper
import nick.mirosh.newsapp.domain.mapper.news.DatabaseToDomainArticleMapper
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import nick.mirosh.newsapp.domain.network.usecase.NetworkConnectivityUseCase
import nick.mirosh.newsapp.domain.usecase.articles.FetchFavoriteArticlesUsecase
import nick.mirosh.newsapp.ui.FeedViewModel
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val IO = "IO"
private const val MAIN = "Main"
private const val DEFAULT = "Default"

val appModule = module {

    single<CoroutineDispatcher>(named(IO)) { Dispatchers.IO }
    single<CoroutineDispatcher>(named(DEFAULT)) { Dispatchers.Default }
    single<CoroutineDispatcher>(named(MAIN)) { Dispatchers.Main }

    singleOf(::NetworkConnectivityManager) { bind<NetworkConnectivityService>() }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<NewsService> { get<Retrofit>().create(NewsService::class.java) }

    single<ArticleDao> {
        Room
            .databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .build()
            .articleDao()
    }
    factoryOf(::DatabaseToDomainArticleMapper)
    factoryOf(::DTOtoDatabaseArticleMapper)
    singleOf(::NewsRemoteDataSource)
    singleOf(::NewsRepositoryImpl) { bind<NewsRepository>() }

    factory { FetchArticlesUsecase(get(), get(named(IO))) }
    factory { LikeArticleUsecase(get(), get(named(IO))) }
    factory { FetchFavoriteArticlesUsecase(get(), get(named(IO))) }

    factoryOf(::NetworkConnectivityUseCase)
    viewModelOf(::FeedViewModel)
    viewModelOf(::FavoriteArticlesViewModel)
}
