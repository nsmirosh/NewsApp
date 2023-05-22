package nick.mirosh.pokeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nick.mirosh.pokeapp.ui.details.DetailsScreenContent
import nick.mirosh.pokeapp.ui.feed.MainScreenContent
import nick.mirosh.pokeapp.ui.theme.PokeAppTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokeAppTheme {
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

                            MainScreenContent(onClick = {
                                val encodedUrl = URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())

                                navController.navigateSingleTopTo("${Details.route}/$encodedUrl")
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
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }


