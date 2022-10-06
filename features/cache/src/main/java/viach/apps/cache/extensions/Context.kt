package viach.apps.cache.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import viach.apps.cache.SavedGame
import viach.apps.cache.SettingsPreferences
import viach.apps.cache.StatsPreferences
import viach.apps.cache.status.SavedGameSerializer
import viach.apps.cache.status.SettingsPreferencesSerializer
import viach.apps.cache.status.StatsPreferencesSerializer

internal val Context.preferences: DataStore<StatsPreferences> by dataStore(
    fileName = "StatsPreferences",
    serializer = StatsPreferencesSerializer
)

val Context.settingsPreferences: DataStore<SettingsPreferences> by dataStore(
    fileName = "StatsSettings",
    serializer = SettingsPreferencesSerializer
)

internal val Context.savedGames: DataStore<SavedGame> by dataStore(
    fileName = "SavedGame",
    serializer = SavedGameSerializer
)