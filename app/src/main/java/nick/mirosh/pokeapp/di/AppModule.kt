package nick.mirosh.pokeapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nick.mirosh.pokeapp.database.AppDatabase
import nick.mirosh.pokeapp.database.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductionModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {

        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }
}