package nick.mirosh.newsapp.ui.feed

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import nick.mirosh.newsapp.R
import nick.mirosh.newsapp.domain.models.Article
import nick.mirosh.newsapp.ui.MainViewModel
import nick.mirosh.newsapp.ui.animations.SmileyAnimation
import nick.mirosh.newsapp.ui.composables.FailedMessage


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenContent(
    viewModel: MainViewModel = viewModel(),
    onArticleClick: (Article) -> Unit,
    onSavedArticlesClicked: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    with(uiState) {
        when (this) {
            is FeedUIState.Idle -> Text(text = "Idle")
            is FeedUIState.Loading -> /*LoadingProgressBar()*/SmileyAnimation()
            is FeedUIState.Failed -> FailedMessage()
            is FeedUIState.Feed ->
                ArticleFeed2(
                    articles = articles,
                    onArticleClick = onArticleClick,
                    onLikeClick = viewModel::onLikeClick,
                    onSavedArticlesClicked = onSavedArticlesClicked
                )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleFeed(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onLikeClick: (Article) -> Unit,
    onSavedArticlesClicked: () -> Unit
) {
    Scaffold(
        content = {
            LazyColumn {
                items(articles, key = { article -> article.url }) { article ->
                    ArticleItem(article, onArticleClick, onLikeClick)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onSavedArticlesClicked,
                modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.save),
                    contentDescription = "Save"
                )
            }
        },

        floatingActionButtonPosition = FabPosition.End,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleFeed2(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onLikeClick: (Article) -> Unit,
    onSavedArticlesClicked: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = TweenSpec(durationMillis = 2000)),
        exit = fadeOut(animationSpec = TweenSpec(durationMillis = 2000))
    ) {
        Scaffold(
            content = {
                LazyColumn {
                    items(articles, key = { article -> article.url }) { article ->
                        ArticleItem(article, onArticleClick, onLikeClick)
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onSavedArticlesClicked,
                    modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.save),
                        contentDescription = "Save"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
        )
    }
}
@Composable
fun ArticleItem(
    article: Article,
    onArticleClick: (Article) -> Unit,
    onLikeCLick: (Article) -> Unit
) {

    Row(
        modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clickable { onArticleClick(article) }
                .height(150.dp)
                .padding(8.dp)
                .width(200.dp)
                .clip(shape = RoundedCornerShape(8.dp)),

            model = article.urlToImage,
            contentDescription = "Translated description of what the image contains"
        )
        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .width(200.dp)
                .height(150.dp)
        ) {
            Text(
                text = article.title,
                lineHeight = 18.sp,
                fontSize = 14.sp
            )
            LikeButton(liked = article.liked) {
                onLikeCLick(article)
            }
        }
    }
}


@Composable
fun BoxScope.LikeButton(
    liked: Boolean,
    onLikeCLick: () -> Unit
) {
    IconButton(
        onClick = {
            onLikeCLick()
        },
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        Icon(
            imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (liked) Color.Red else Color.Black,
            modifier = Modifier.size(24.dp)
        )
    }
}

