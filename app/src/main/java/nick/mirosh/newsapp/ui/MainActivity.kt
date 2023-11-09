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
import nick.mirosh.newsapp.ui.feed.FeedScreen
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
                            FeedScreen(
                                viewModel = hiltViewModel(),
                                onArticleClick = {
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
                            DetailsScreenContent(
                                articleUrl = it.arguments?.getString(Details.articleArg).orEmpty()
                            )
                        }

                        composable(
                            route = FavoriteArticles.route,
                        ) {
                            FavoriteArticlesScreenContent(viewModel = hiltViewModel())
                        }
                    }
                }
            }
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }


