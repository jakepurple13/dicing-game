package viach.apps.cache.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import viach.apps.cache.StatsPreferences
import viach.apps.cache.status.StatsPreferencesSerializer

internal val Context.preferences: DataStore<StatsPreferences> by dataStore(
    fileName = "StatsPreferences",
    serializer = StatsPreferencesSerializer
)

public val Context.settingPreferences: DataStore<Preferences> by preferencesDataStore(name = "StatsSettings")