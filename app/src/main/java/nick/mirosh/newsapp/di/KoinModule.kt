package nick.mirosh.newsapp.di

import androidx.room.Room
import nick.mirosh.newsapp.BuildConfig
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.data.database.DATABASE_NAME
import nick.mirosh.newsapp.data.networking.HeaderInterceptor
import nick.mirosh.newsapp.data.networking.NewsService
import nick.mirosh.newsapp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsapp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsapp.domain.feed.repository.NewsRepository
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsapp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsapp.ui.FeedViewModel
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    //https://insert-koin.io/docs/reference/introduction/
    factoryOf(::FetchArticlesUsecase)
    factoryOf(::LikeArticleUsecase)
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
                androidApplication().applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .build()
            .articleDao()
    }
    singleOf(::NewsRemoteDataSource)
    singleOf(::NewsRepositoryImpl) { bind<NewsRepository>() }
    viewModelOf(::FeedViewModel)
    viewModelOf(::FavoriteArticlesViewModel)
}
