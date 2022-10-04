package viach.apps.dicing.ui.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import viach.apps.dicing.R
import viach.apps.dicing.model.AIDifficulty
import viach.apps.dicing.ui.theme.spacing

@Composable
fun AIDifficultyDialog(
    onDifficultySelected: (AIDifficulty) -> Unit,
    onDismissIntent: () -> Unit
) {
    Dialog(onDismissRequest = onDismissIntent) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(bottom = MaterialTheme.spacing.l),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.l)
        ) {
            Box(modifier = Modifier.background(MaterialTheme.colors.primary)) {
                Text(
                    text = stringResource(R.string.select_difficulty),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
                )
            }
            MaxWidthButton(
                textRes = R.string.easy,
                onClick = {
                    onDifficultySelected(AIDifficulty.EASY)
                    onDismissIntent()
                }
            )
            MaxWidthButton(
                textRes = R.string.normal,
                onClick = {
                    onDifficultySelected(AIDifficulty.NORMAL)
                    onDismissIntent()
                }
            )
            MaxWidthButton(
                textRes = R.string.hard,
                onClick = {
                    onDifficultySelected(AIDifficulty.HARD)
                    onDismissIntent()
                }
            )
        }
    }
}