package nick.mirosh.newsapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nick.mirosh.newsapp.database.AppDatabase
import nick.mirosh.newsapp.database.ArticleDao
import nick.mirosh.newsapp.database.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): ArticleDao {
        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
            .articleDao()
    }
}