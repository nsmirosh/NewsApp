package nick.mirosh.newsapp.ui.favorite_articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.ui.composables.FailedMessage
import nick.mirosh.newsapp.ui.composables.LoadingProgressBar

@Composable
fun FavoriteArticlesScreenContent(
    modifier: Modifier = Modifier,
    viewModel: FavoriteArticlesViewModel,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    with(uiState) {
        when (this) {
            is FavoriteArticlesUIState.FavoriteArticles ->
                Articles(articles = articles)

            is FavoriteArticlesUIState.Idle ->
                Text(text = "Idle")

            is FavoriteArticlesUIState.Loading ->
                LoadingProgressBar()

            is FavoriteArticlesUIState.Failed ->
                FailedMessage(message = "Could not load favorite articles")

            is FavoriteArticlesUIState.FavoriteArticlesEmpty -> NoArticles()
        }
    }
}

@Composable
fun Articles(
    modifier: Modifier = Modifier,
    articles: List<Article>
) {
    LazyColumn {
        items(articles.size) { index ->
            val article = articles[index]
            Row(
                modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(150.dp)
                        .padding(8.dp)
                        .width(200.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),

                    model = article.urlToImage,
                    contentDescription = "Translated description of what the image contains"
                )
                Text(

                    text = article.title,
                    lineHeight = 18.sp,
                    fontSize = 14.sp

                )
            }
        }
    }
}

@Composable
fun NoArticles(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No saved articles",
            fontSize = 24.sp,
        )
    }
}
