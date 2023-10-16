package nick.mirosh.newsapp.ui.favorite_articles

import androidx.compose.runtime.snapshots.SnapshotStateList
import nick.mirosh.newsapp.domain.models.Article

sealed class FavoriteArticlesUIState {
    data class Feed(val articles: SnapshotStateList<Article>) : FavoriteArticlesUIState()
    data object Idle : FavoriteArticlesUIState()
    data object Loading : FavoriteArticlesUIState()
    data object Failed : FavoriteArticlesUIState()
}
