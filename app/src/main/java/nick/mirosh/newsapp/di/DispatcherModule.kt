package nick.mirosh.newsapp.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO = "IO"
const val MAIN = "Main"
const val DEFAULT = "Default"
const val UNCONFINED = "Unconfined"

val dispatcherModule = module {

    single<CoroutineDispatcher>(named(IO)) { Dispatchers.IO }
    single<CoroutineDispatcher>(named(DEFAULT)) { Dispatchers.Default }
    single<CoroutineDispatcher>(named(MAIN)) { Dispatchers.Main }
    single<CoroutineDispatcher>(named(UNCONFINED)) { Dispatchers.Unconfined }

}