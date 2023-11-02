package nick.mirosh.newsapp.ui.feed

import nick.mirosh.newsapp.domain.models.Article

sealed class FeedUIState {
    data class Feed(val articles: List<Article>) : FeedUIState()
    data object Idle : FeedUIState()
    data object Loading : FeedUIState()
    data object Failed : FeedUIState()
}
