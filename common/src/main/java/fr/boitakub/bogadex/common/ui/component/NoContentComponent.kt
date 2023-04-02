package fr.boitakub.bogadex.common.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.boitakub.bogadex.common.R
import fr.boitakub.bogadex.common.ui.theme.BogadexTheme

@Composable
fun NoContentComponent(
    errorMessage: String? = null,
    onRetryClicked: (() -> Unit)? = null,
) {
    val stroke = Stroke(
        width = 6f,
    )
    Box(
        Modifier
            .wrapContentSize(Alignment.Center)
            .padding(16.dp)
            .defaultMinSize(500.dp, 100.dp)
            .focusable(false)
            .drawBehind {
                drawRoundRect(color = Color.Black, style = stroke)
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.wrapContentSize().padding(16.dp),
            textAlign = TextAlign.Center,
            text = errorMessage ?: stringResource(id = R.string.no_content),
        )
        if (onRetryClicked != null) {
            Button(
                onClick = onRetryClicked,
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

//region Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun NoComponentViewLight() {
    BogadexTheme {
        NoContentComponent()
    }
}

//endregion
