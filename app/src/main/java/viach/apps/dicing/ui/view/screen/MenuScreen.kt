package viach.apps.dicing.ui.view.screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import viach.apps.cache.settings.SavedGameCache
import viach.apps.cache.settings.SettingsCache
import viach.apps.dicing.R
import viach.apps.dicing.model.AIDifficulty
import viach.apps.dicing.ui.theme.spacing
import viach.apps.dicing.ui.view.component.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuScreen(
    savedGameCache: SavedGameCache,
    settingsCache: SettingsCache,
    onPlayOpenIntent: (AIDifficulty) -> Unit,
    onContinueGameOpenIntent: () -> Unit,
    onTwoPlayersOpenIntent: () -> Unit,
    onStatsOpenIntent: () -> Unit,
    onRulesOpenIntent: () -> Unit,
    onSettingsOpenIntent: () -> Unit,
) {
    var showAIDifficultyDialog by rememberSaveable { mutableStateOf(false) }
    val useDialog by settingsCache.useDifficultyDialog.collectAsState(false)

    if (showAIDifficultyDialog && useDialog) {
        AIDifficultyDialog(
            onDifficultySelected = onPlayOpenIntent,
            onDismissIntent = { showAIDifficultyDialog = false }
        )
    }

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
        var showClearDialog by remember { mutableStateOf(false) }

        CustomDialog(
            isVisible = showClearDialog,
            title = "Clear Saved Game",
            message = "Are you sure you want to clear your last game?",
            confirmButton = {
                MaxWidthButton(text = "Yes") {
                    showClearDialog = false
                    savedGameCache.clearGame()
                }
            },
            onDismissIntent = { showClearDialog = false }
        )

        AnimatedVisibility(
            visible = savedGameCache.hasSavedGame().collectAsState(false).value,
            enter = fadeIn() + expandVertically() + slideInHorizontally(),
            exit = fadeOut() + shrinkVertically() + slideOutHorizontally(),
        ) {
            SwipeToDismiss(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.l),
                state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            showClearDialog = true
                        }
                        false
                    }
                ),
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                            .padding(end = MaterialTheme.spacing.xxl),
                        contentAlignment = Alignment.CenterEnd
                    ) { Icon(Icons.Default.Close, contentDescription = null) }
                }
            ) {
                MaxWidthButton(
                    text = "Continue Last Game",
                    onClick = onContinueGameOpenIntent
                )
            }
        }
        MaxWidthButton(
            textRes = if (showAIDifficultyDialog && !useDialog) R.string.select_difficulty else R.string.play,
            onClick = { showAIDifficultyDialog = !showAIDifficultyDialog }
        )
        if (!useDialog) {
            AnimatedVisibility(
                visible = showAIDifficultyDialog,
                enter = fadeIn() + expandVertically() + slideInHorizontally { it / 2 },
                exit = fadeOut() + shrinkVertically() + slideOutHorizontally { it / 2 },
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
        VerticalSpacer(MaterialTheme.spacing.l)
        MaxWidthButton(
            textRes = R.string.settings,
            onClick = onSettingsOpenIntent
        )
    }
}