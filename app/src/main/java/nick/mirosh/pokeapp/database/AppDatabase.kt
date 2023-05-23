package nick.mirosh.pokeapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.mirosh.pokeapp.entity.ArticleDTO

const val DATABASE_NAME = "articles-db"

@Database(entities = [ArticleDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

}