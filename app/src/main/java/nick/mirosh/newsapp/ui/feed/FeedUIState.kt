package nick.mirosh.newsapp.ui.feed

import androidx.compose.runtime.snapshots.SnapshotStateList
import nick.mirosh.newsapp.domain.models.Article

sealed class FeedUIState {
    data class Feed(val articles: SnapshotStateList<Article>) : FeedUIState()
    data object Idle : FeedUIState()
    data object Loading : FeedUIState()
    data object Failed : FeedUIState()
}
