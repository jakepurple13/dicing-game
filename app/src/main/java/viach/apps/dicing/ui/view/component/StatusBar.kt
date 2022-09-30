package viach.apps.dicing.ui.view.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import viach.apps.dicing.dice.Dice
import viach.apps.dicing.dicefactory.RandomDiceFactory

@Composable
fun StatusBar(
    leftScore: Int,
    rightScore: Int,
    dice: Dice?
) {
    var newDie by remember { mutableStateOf(dice) }

    LaunchedEffect(dice) {
        repeat(6) {
            newDie = RandomDiceFactory.create()
            delay(50)
        }
        newDie = dice
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = animateIntAsState(leftScore).value.toString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Box(modifier = Modifier.size(100.dp)) {
            if (newDie != null) {
                Icon(
                    painter = painterResource(newDie!!.iconRes),
                    contentDescription = stringResource(newDie!!.contentDescriptionRes),
                    modifier = Modifier.matchParentSize(),
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
        Text(
            text = animateIntAsState(rightScore).value.toString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}