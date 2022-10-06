package viach.apps.dicing.ui.view.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import viach.apps.cache.SystemThemeMode
import viach.apps.cache.settings.SettingsCache
import viach.apps.dicing.R
import viach.apps.dicing.ui.theme.Theme
import viach.apps.dicing.ui.theme.spacing
import viach.apps.dicing.ui.view.component.GroupButton
import viach.apps.dicing.ui.view.component.GroupButtonModel
import viach.apps.dicing.ui.view.component.VerticalSpacer

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(settingsCache: SettingsCache) {
    Column(modifier = Modifier.fillMaxWidth()) {
        VerticalSpacer(MaterialTheme.spacing.l)
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
        VerticalSpacer(MaterialTheme.spacing.l)
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(stringResource(id = R.string.settings)) })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.l)
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.l)
            ) {
                val difficulty by settingsCache.useDifficultyDialog.collectAsState(initial = false)
                ListItem(
                    modifier = Modifier.clickable { settingsCache.setUseDifficultyDialog(!difficulty) },
                    text = { Text("Show a Dialog on Difficulty Selection?") },
                    trailing = {
                        Switch(
                            checked = difficulty,
                            onCheckedChange = { settingsCache.setUseDifficultyDialog(it) }
                        )
                    }
                )

                Divider()

                Column {
                    val currentTheme by settingsCache.theme.collectAsState(0)
                    val state by settingsCache.themeMode.collectAsState(SystemThemeMode.FollowSystem)

                    ListItem(
                        text = { Text("Select Theme Mode") },
                        trailing = {
                            GroupButton(
                                selected = state,
                                options = listOf(
                                    GroupButtonModel(SystemThemeMode.Day) { Text("Day") },
                                    GroupButtonModel(SystemThemeMode.Night) { Text("Night") },
                                    GroupButtonModel(SystemThemeMode.FollowSystem) { Text("Follow System") },
                                ),
                                onClick = settingsCache::setThemeMode
                            )
                        }
                    )

                    ListItem(text = { Text("Select Theme") })

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.s),
                        contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.l)
                    ) {
                        items(Theme.values()) { theme ->
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { settingsCache.setTheme(theme.ordinal) }
                                    .background(theme.getTheme(isSystemInDarkTheme()).background, CircleShape)
                                    .size(80.dp)
                                    .border(
                                        4.dp,
                                        animateColorAsState(if (currentTheme == theme.ordinal) Color.Green else Color.White).value,
                                        CircleShape
                                    )
                            )
                        }
                    }
                }
                Divider()
            }
        }
    }
}
