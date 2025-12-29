package nick.mirosh.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import nick.mirosh.newsapp.ui.details.DetailsScreenContent
import nick.mirosh.newsapp.ui.favorite_articles.FavoriteArticlesScreenContent
import nick.mirosh.newsapp.ui.feed.FeedScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicActivity()
        }
    }
}


data object NewsListKey
data class NewsDetailsKey(val url: String)
data object SavedArticlesKey

@Composable
fun BasicActivity() {

    val backStack = remember { mutableStateListOf<Any>(NewsListKey) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<NewsListKey> {
                FeedScreen(
                    onArticleClick = {
                        backStack.add(NewsDetailsKey(it.url))

                    },
                    onSavedArticlesClicked = {
                        backStack.add(SavedArticlesKey)
                    }
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


