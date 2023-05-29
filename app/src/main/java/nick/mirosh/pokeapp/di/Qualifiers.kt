package nick.mirosh.pokeapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Universal

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Cache