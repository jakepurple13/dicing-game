package viach.apps.dicing.ui.view.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import viach.apps.dicing.R
import viach.apps.dicing.game.Game
import viach.apps.dicing.gamefield.GameField
import viach.apps.dicing.ui.theme.spacing

@Composable
fun GameBar(
    gameField: GameField,
    game: Game,
    rowCellsCount: Int,
    playerPosition: Int = 0,
    layoutPadding: PaddingValues = PaddingValues(12.dp),
    itemPadding: PaddingValues = PaddingValues(MaterialTheme.spacing.m),
    itemsClickable: Boolean = true,
    onPlaceDiceIntent: (position: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(layoutPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (playerPosition == 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center
            ) {
                val isPlayer2Move = game.isPlayerMove(2)
                if (isPlayer2Move) Icon(Icons.Default.ArrowForward, null)
                Text(stringResource(R.string.player_location, playerPosition))
                if (isPlayer2Move) Icon(Icons.Default.ArrowBack, null)
            }
        }
        for (y in 0 until rowCellsCount) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (x in 0 until rowCellsCount) {
                    val position = y * 3 + x + 1
                    val dice = gameField.getDice(position)
                    val clickable = itemsClickable && gameField.isFree(position)
                    Crossfade(
                        targetState = dice,
                        modifier = Modifier
                            .weight(1f)
                            .padding(itemPadding)
                    ) { target ->
                        Icon(
                            painter = painterResource(target.iconRes),
                            contentDescription = stringResource(target.contentDescriptionRes),
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .clickable(clickable) { onPlaceDiceIntent(position) }
                                .aspectRatio(1f),
                            tint = MaterialTheme.colors.primaryVariant
                        )
                    }
                }
            }
        }
        if (playerPosition == 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center
            ) {
                val isPlayer1Move = game.isPlayerMove(1)
                if (isPlayer1Move) Icon(Icons.Default.ArrowForward, null)
                Text(stringResource(R.string.player_location, playerPosition))
                if (isPlayer1Move) Icon(Icons.Default.ArrowBack, null)
            }
        }
    }
}