package viach.apps.dicing.ui.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import viach.apps.dicing.R
import viach.apps.dicing.game.Game
import viach.apps.dicing.ui.theme.spacing
import viach.apps.dicing.ui.view.component.GameBar
import viach.apps.dicing.ui.view.component.MaxWidthButton
import viach.apps.dicing.ui.view.component.StatusBar
import viach.apps.dicing.ui.view.component.VerticalSpacer

@Suppress("NAME_SHADOWING")
@Composable
fun RulesScreen(
    game: Game,
    onBackToMenuIntent: () -> Unit
) {
    val scrollState = rememberScrollState()
    var game by rememberSaveable { mutableStateOf(game) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.rules)) },
                backgroundColor = MaterialTheme.colors.background,
                navigationIcon = { IconButton(onClick = onBackToMenuIntent) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.l),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.l)
            ) {
                Text(
                    text = stringResource(R.string.rules_description_part_1),
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.playing_field_preview_below),
                    textAlign = TextAlign.Justify
                )
                GameBar(
                    gameField = game.getGameField(1),
                    rowCellsCount = 3,
                    onPlaceDiceIntent = { position ->
                        game = game.makeMove(position, 1).createNextDice()
                        if (game.gameOver) {
                            coroutineScope.launch {
                                delay(2000)
                                game = game.newGame
                            }
                        }
                    }
                )
                Text(
                    text = stringResource(R.string.status_bar_preview_below),
                    textAlign = TextAlign.Justify
                )
                StatusBar(
                    leftScore = game.getGameField(1).score,
                    rightScore = 0,
                    dice = game.nextDice!!
                )
                Text(
                    text = stringResource(R.string.rules_description_part_2),
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = stringResource(R.string.rules_description_part_3),
                    textAlign = TextAlign.Justify
                )
            }
            MaxWidthButton(
                textRes = R.string.back_to_menu,
                onClick = onBackToMenuIntent,
            )
            VerticalSpacer(MaterialTheme.spacing.l)
        }
    }
}