package nick.mirosh.newsapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Universal

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Cache