package nick.mirosh.newsapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingProgressBar() {
    Box {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
        )
    }
}