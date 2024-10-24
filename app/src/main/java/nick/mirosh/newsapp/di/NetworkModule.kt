package nick.mirosh.newsapp.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import nick.mirosh.newsapp.BuildConfig
import nick.mirosh.newsapp.data.repository.NetworkConnectivityManager
import nick.mirosh.newsapp.domain.network.repository.NetworkConnectivityService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val networkModule = module {
    single<HttpClient> {
        HttpClient(OkHttp) {

            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "newsapi.org/v2/"
                    header("X-Api-Key", BuildConfig.API_KEY)
                }
            }
        }
    }

    singleOf(::NetworkConnectivityManager) { bind<NetworkConnectivityService>() }
}
