package viach.apps.dicing.ui.view.screen

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import viach.apps.cache.settings.SettingsCache
import viach.apps.dicing.R
import viach.apps.dicing.ui.theme.LocalActivity
import viach.apps.dicing.ui.theme.Theme
import viach.apps.dicing.ui.theme.spacing
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

                val currentTheme by settingsCache.theme.collectAsState(0)
                var showModeDialog by remember { mutableStateOf(false) }

                Column {
                    val state = when (AppCompatDelegate.getDefaultNightMode()) {
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> ThemeMode.FollowSystem
                        AppCompatDelegate.MODE_NIGHT_NO -> ThemeMode.Day
                        AppCompatDelegate.MODE_NIGHT_YES -> ThemeMode.Night
                        else -> ThemeMode.FollowSystem
                    }

                    val activity = LocalActivity.current

                    if (showModeDialog) {
                        fun changeMode(mode: ThemeMode) {
                            AppCompatDelegate.setDefaultNightMode(mode.id)
                            activity.recreate()
                        }

                        AlertDialog(
                            onDismissRequest = { showModeDialog = false },
                            title = { Text("Select Theme Mode") },
                            text = {
                                Column {
                                    Spacer(Modifier.height(MaterialTheme.spacing.s))
                                    ListItem(
                                        modifier = Modifier.clickable { changeMode(ThemeMode.Day) },
                                        text = { Text("Day") },
                                        icon = {
                                            RadioButton(
                                                selected = state == ThemeMode.Day,
                                                onClick = { changeMode(ThemeMode.Day) }
                                            )
                                        }
                                    )

                                    ListItem(
                                        modifier = Modifier.clickable { changeMode(ThemeMode.Night) },
                                        text = { Text("Night") },
                                        icon = {
                                            RadioButton(
                                                selected = state == ThemeMode.Night,
                                                onClick = { changeMode(ThemeMode.Night) }
                                            )
                                        }
                                    )

                                    ListItem(
                                        modifier = Modifier.clickable { changeMode(ThemeMode.FollowSystem) },
                                        text = { Text("Follow System") },
                                        icon = {
                                            RadioButton(
                                                selected = state == ThemeMode.FollowSystem,
                                                onClick = { changeMode(ThemeMode.FollowSystem) }
                                            )
                                        }
                                    )
                                }
                            },
                            confirmButton = { TextButton(onClick = { showModeDialog = false }) { Text("Done") } }
                        )
                    }

                    ListItem(
                        modifier = Modifier.clickable { showModeDialog = true },
                        text = { Text("Select Theme Mode") },
                        trailing = { Text(state.name) }
                    )

                    ListItem(text = { Text("Select Theme") })

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.s),
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.m)
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
            }
        }
    }
}

enum class ThemeMode(val id: Int) {
    Day(AppCompatDelegate.MODE_NIGHT_NO),
    Night(AppCompatDelegate.MODE_NIGHT_YES),
    FollowSystem(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}