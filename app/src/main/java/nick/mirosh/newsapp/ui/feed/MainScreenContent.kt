package nick.mirosh.newsapp.ui.feed

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import nick.mirosh.newsapp.entity.Article
import nick.mirosh.newsapp.ui.MainViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    onClick: (Article) -> Unit,
    onSavedArticlesClicked: () -> Unit
) {
    val articles by viewModel.articles.collectAsStateWithLifecycle(listOf())

    Scaffold(
        topBar = {
            SavedArticlesAppBar(onSavedArticlesClicked)
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(top = 80.dp)
            ) {
                items(articles.size) { index ->
                    ArticleItem(articles[index], onClick, viewModel::onLikeClick)
                }
            }
        }
    )
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
            IconButton(
                onClick = { onLikeCLick(article) },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedArticlesAppBar(onSavedArticlesClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Saved Articles",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier
            .height(80.dp)
            .padding(16.dp)
            .background(color = Color.Blue)
            .clickable {
                onSavedArticlesClicked()
            }
    )
}