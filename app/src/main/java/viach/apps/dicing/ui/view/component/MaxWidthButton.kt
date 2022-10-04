package viach.apps.dicing.ui.view.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import viach.apps.dicing.ui.theme.spacing

@Composable
fun MaxWidthButton(
    text: String,
    padding: PaddingValues = PaddingValues(end = MaterialTheme.spacing.xxl),
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        onClick = onClick
    ) {
        Text(text = text)
    }
}


@Composable
fun MaxWidthButton(
    @StringRes textRes: Int,
    padding: PaddingValues = PaddingValues(end = MaterialTheme.spacing.xxl),
    onClick: () -> Unit
) = MaxWidthButton(
    text = stringResource(textRes),
    padding = padding,
    onClick = onClick
)