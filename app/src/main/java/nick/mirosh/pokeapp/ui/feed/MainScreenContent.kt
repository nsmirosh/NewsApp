package nick.mirosh.pokeapp.ui.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import nick.mirosh.pokeapp.entity.ArticleDTO
import nick.mirosh.pokeapp.ui.MainViewModel


@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    onClick: (ArticleDTO) -> Unit
) {
    val articles by viewModel.pokemonList.collectAsStateWithLifecycle()

    LazyColumn {
        items(articles.size) { index ->
            val article = articles[index]
            Row(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    modifier = Modifier
                        .clickable { onClick(article) }
                        .clip(shape = RoundedCornerShape(8.dp)),
                    model = article.urlToImage,
                    contentDescription = "Translated description of what the image contains"
                )
                Text(text = article.title)
            }
        }
    }
}