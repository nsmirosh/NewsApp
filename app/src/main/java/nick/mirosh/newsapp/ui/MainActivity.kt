package nick.mirosh.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import nick.mirosh.newsapp.ui.details.DetailsScreenContent
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesScreenContent
import nick.mirosh.newsapp.ui.feed.FeedScreen
import nick.mirosh.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                BasicActivity()
            }
        }
    }
}


data object NewsListKey
data class NewsDetailsKey(val url: String)
data object SavedArticlesKey

@Composable
fun BasicActivity() {

    val backStack = remember { mutableStateListOf<Any>(NewsListKey) }
    val currentKey = backStack.lastOrNull()
    val showBottomBar = currentKey is NewsListKey || currentKey is SavedArticlesKey

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NewsBottomBar(
                    currentKey = currentKey,
                    onFeedClick = {
                        if (currentKey !is NewsListKey) {
                            backStack.clear()
                            backStack.add(NewsListKey)
                        }
                    },
                    onSavedClick = {
                        if (currentKey !is SavedArticlesKey) {
                            backStack.add(SavedArticlesKey)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<NewsListKey> {
                    FeedScreen(
                        onArticleClick = {
                            backStack.add(NewsDetailsKey(it.url))
                        },
                    )
                }
                entry<NewsDetailsKey> { key ->
                    DetailsScreenContent(articleUrl = key.url)
                }
                entry<SavedArticlesKey> {
                    FavoriteArticlesScreenContent()
                }
            }
        )
    }
}

@Composable
fun NewsBottomBar(
    currentKey: Any?,
    onFeedClick: () -> Unit,
    onSavedClick: () -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentKey is NewsListKey,
            onClick = onFeedClick,
            icon = { Icon(Icons.Default.Home, contentDescription = "Feed") },
            label = { Text("Feed") },
        )
        NavigationBarItem(
            selected = currentKey is SavedArticlesKey,
            onClick = onSavedClick,
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Saved") },
            label = { Text("Saved") },
        )
    }
}
