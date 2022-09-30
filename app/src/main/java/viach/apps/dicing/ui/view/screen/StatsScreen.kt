package viach.apps.dicing.ui.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import viach.apps.cache.StatsPreferences
import viach.apps.cache.status.StatsCache
import viach.apps.dicing.R
import viach.apps.dicing.ui.view.component.MaxWidthButton
import viach.apps.dicing.ui.view.component.VerticalSpacer

@Composable
fun StatsScreen(
    stats: StatsCache
) {
    val currentStats: StatsPreferences by stats.all.collectAsState(StatsPreferences.newBuilder().build())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        VerticalSpacer(16.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .background(MaterialTheme.colors.onBackground, RoundedCornerShape(4.dp))
                    .size(width = 100.dp, height = 8.dp)
            )
        }
        VerticalSpacer(32.dp)
        Text(
            text = stringResource(R.string.wins_losses),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            style = MaterialTheme.typography.body2
        )
        VerticalSpacer(32.dp)
        Row(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = stringResource(R.string.easy),
                modifier = Modifier.weight(1f)
            )
            Text(text = "${currentStats.easyModeWinsCount} - ${currentStats.easyModeLossesCount}")
        }
        VerticalSpacer(16.dp)
        Row(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = stringResource(R.string.normal),
                modifier = Modifier.weight(1f)
            )
            Text(text = "${currentStats.normalModeWinsCount} - ${currentStats.normalModeLossesCount}")
        }
        VerticalSpacer(16.dp)
        Row(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = stringResource(R.string.hard),
                modifier = Modifier.weight(1f)
            )
            Text(text = "${currentStats.hardModeWinsCount} - ${currentStats.hardModeLossesCount}")
        }
        VerticalSpacer(16.dp)
        Row(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = stringResource(R.string.easy_high_score),
                modifier = Modifier.weight(1f)
            )
            Text(text = "${currentStats.easyModeHighestScore}")
        }
        VerticalSpacer(16.dp)
        Row(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = stringResource(R.string.normal_high_score),
                modifier = Modifier.weight(1f)
            )
            Text(text = "${currentStats.normalModeHighestScore}")
        }
        VerticalSpacer(16.dp)
        Row(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = stringResource(R.string.hard_high_score),
                modifier = Modifier.weight(1f)
            )
            Text(text = "${currentStats.hardModeHighestScore}")
        }
        VerticalSpacer(32.dp)
        MaxWidthButton(
            textRes = R.string.reset_stats,
            onClick = stats::clear
        )
        VerticalSpacer(32.dp)
    }
}