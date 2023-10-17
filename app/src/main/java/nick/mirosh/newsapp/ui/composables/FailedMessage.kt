package nick.mirosh.newsapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FailedMessage(message: String = "Failed to load articles") {

    Box {
        Text(
            text = message,
            style = TextStyle(
                color = Color.Red,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}
