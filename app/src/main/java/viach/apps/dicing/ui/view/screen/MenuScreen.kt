package viach.apps.dicing.ui.view.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import viach.apps.dicing.R
import viach.apps.dicing.model.AIDifficulty
import viach.apps.dicing.ui.theme.spacing
import viach.apps.dicing.ui.view.component.HorizontalSpacer
import viach.apps.dicing.ui.view.component.MaxWidthButton
import viach.apps.dicing.ui.view.component.ReverseMaxWidthButton
import viach.apps.dicing.ui.view.component.VerticalSpacer

@Composable
fun MenuScreen(
    onPlayOpenIntent: (AIDifficulty) -> Unit,
    onTwoPlayersOpenIntent: () -> Unit,
    onStatsOpenIntent: () -> Unit,
    onRulesOpenIntent: () -> Unit,
) {
    var showAIDifficultyDialog by rememberSaveable { mutableStateOf(false) }

    /*if (showAIDifficultyDialog) {
        AIDifficultyDialog(
            onDifficultySelected = onPlayOpenIntent,
            onDismissIntent = { showAIDifficultyDialog = false }
        )
    }*/

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = stringResource(R.string.app_name),
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.size(48.dp)
            )
            HorizontalSpacer(MaterialTheme.spacing.l)
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.h1
            )
        }
        VerticalSpacer(MaterialTheme.spacing.xxl)
        MaxWidthButton(
            textRes = if (showAIDifficultyDialog) R.string.select_difficulty else R.string.play,
            onClick = { showAIDifficultyDialog = !showAIDifficultyDialog }
        )
        AnimatedVisibility(
            visible = showAIDifficultyDialog
        ) {
            Column(
                modifier = Modifier.padding(top = MaterialTheme.spacing.l),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.l)
            ) {
                ReverseMaxWidthButton(
                    textRes = R.string.easy,
                    onClick = {
                        showAIDifficultyDialog = false
                        onPlayOpenIntent(AIDifficulty.EASY)
                    }
                )
                ReverseMaxWidthButton(
                    textRes = R.string.normal,
                    onClick = {
                        showAIDifficultyDialog = false
                        onPlayOpenIntent(AIDifficulty.NORMAL)
                    }
                )
                ReverseMaxWidthButton(
                    textRes = R.string.hard,
                    onClick = {
                        showAIDifficultyDialog = false
                        onPlayOpenIntent(AIDifficulty.HARD)
                    }
                )
            }
        }
        VerticalSpacer(MaterialTheme.spacing.l)
        MaxWidthButton(
            textRes = R.string.two_players,
            onClick = onTwoPlayersOpenIntent
        )
        VerticalSpacer(MaterialTheme.spacing.l)
        MaxWidthButton(
            textRes = R.string.stats,
            onClick = onStatsOpenIntent
        )
        VerticalSpacer(MaterialTheme.spacing.l)
        MaxWidthButton(
            textRes = R.string.rules,
            onClick = onRulesOpenIntent
        )
    }
}