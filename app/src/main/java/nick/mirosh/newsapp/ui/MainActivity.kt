package nick.mirosh.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nick.mirosh.newsapp.ui.details.DetailsScreenContent
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesScreenContent
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesViewModel
import nick.mirosh.newsapp.ui.feed.MainScreenContent
import nick.mirosh.newsapp.ui.theme.NewsAppTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsAppTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Feed.route,
                        modifier = Modifier
                    ) {
                        composable(route = Feed.route) {
                            val viewModel = hiltViewModel<MainViewModel>()

                            MainScreenContent(
                                viewModel = viewModel,
                                onClick = {
                                    val encodedUrl =
                                        URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())

                                    navController.navigateSingleTopTo("${Details.route}/$encodedUrl")

                                },
                                onSavedArticlesClicked = {
                                    navController.navigateSingleTopTo(FavoriteArticles.route)
                                })
                        }
                        composable(
                            route = Details.routeWithArgs,
                            arguments = Details.arguments
                        ) {
                            val articleUrl =
                                it.arguments?.getString(Details.articleArg)

                            DetailsScreenContent(articleUrl = articleUrl.orEmpty())
                        }

                        composable(
                            route = FavoriteArticles.route,
                        ) {
                            val viewModel = hiltViewModel<FavoriteArticlesViewModel>()

                            FavoriteArticlesScreenContent(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }


