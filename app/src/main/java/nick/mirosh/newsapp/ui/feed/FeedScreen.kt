package nick.mirosh.newsapp.ui.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import nick.mirosh.newsapp.R
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.ui.FeedViewModel
import nick.mirosh.newsapp.ui.animations.SmileyProgressAnimation
import nick.mirosh.newsapp.ui.composables.FailedMessage
import nick.mirosh.newsapp.ui.feed.FeedUIState.Failed
import nick.mirosh.newsapp.ui.feed.FeedUIState.Feed
import nick.mirosh.newsapp.ui.feed.FeedUIState.Loading
import nick.mirosh.newsapp.ui.feed.FeedUIState.NoNetworkConnection
import nick.mirosh.newsapp.ui.theme.NewsAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = koinViewModel(),
    onArticleClick: (Article) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    with(uiState) {
        when (this) {
            is Failed -> FailedMessage()
            is Feed ->
                ArticleFeed(
                    modifier = modifier,
                    articles = articles,
                    onArticleClick = onArticleClick,
                    onLikeClick = viewModel::onLikeClick,
                )

            is NoNetworkConnection -> NoNetworkState()
            is Loading -> SmileyProgressAnimation()
            is FeedUIState.Empty -> FailedMessage(message = "No articles for this country :( ")
        }
    }
}

@Composable
fun ArticleFeed(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onLikeClick: (Article) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(articles, key = { article -> article.url }) { article ->
                ArticleItem(
                    article = article,
                    onArticleClick = onArticleClick,
                    onLikeClick = onLikeClick
                )
            }
        }
    }
}


@Composable
fun LikeButton(
    liked: Boolean,
    modifier: Modifier = Modifier,
    onLikeCLick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onLikeCLick()
        },
    ) {
        Icon(
            imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (liked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (Article) -> Unit,
    onLikeClick: (Article) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onArticleClick(article) }
            .testTag("article_item"),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "Article image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = article.author,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )
                    LikeButton(liked = article.liked) {
                        onLikeClick(article)
                    }
                }
            }
        }
    }
}

@Composable
fun NoNetworkState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .testTag("no_network_connection_image")
                .padding(horizontal = 54.dp),
            painter = painterResource(id = R.drawable.wifi),
            contentDescription = "Network connection",
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 0.dp),
            text = stringResource(R.string.no_network_connection),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun ArticleItemPreview() {
    NewsAppTheme {
        ArticleItem(
            article = Article(
                author = "Jane Doe",
                content = "",
                description = "A short summary of what this article is about, " +
                    "spanning a couple of lines to show the layout.",
                publishedAt = "2026-06-23",
                title = "A reasonably long headline that may wrap onto two lines",
                url = "https://example.com",
                urlToImage = "",
                liked = true,
            ),
            onArticleClick = {},
            onLikeClick = {},
        )
    }
}

@Preview
@Composable
fun NoNetworkStatePreview() {
    NewsAppTheme {
        NoNetworkState()
    }
}
