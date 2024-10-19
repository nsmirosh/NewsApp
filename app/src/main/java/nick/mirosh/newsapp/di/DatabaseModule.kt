package nick.mirosh.newsapp.di

import android.content.Context
import androidx.room.Room
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.data.database.DATABASE_NAME

class DatabaseModule {

    fun provideAppDatabase(appContext: Context): ArticleDao {
        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
            .articleDao()
    }
}