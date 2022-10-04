package viach.apps.cache.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import viach.apps.cache.extensions.settingPreferences

class SettingsCacheImpl(
    context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : SettingsCache {
    private val preferences: DataStore<Preferences> = context.settingPreferences

    override val all: Flow<Preferences> get() = preferences.data

    override val theme: Flow<Int> get() = preferences.data.map { it[THEME_KEY] ?: 0 }

    override fun setTheme(themeIndex: Int) {
        coroutineScope.launch { preferences.edit { it[THEME_KEY] = themeIndex } }
    }

    override val useDifficultyDialog: Flow<Boolean>
        get() = preferences.data.map { it[USE_DIFFICULTY_DIALOG] ?: false }

    override fun setUseDifficultyDialog(useDialog: Boolean) {
        coroutineScope.launch { preferences.edit { it[USE_DIFFICULTY_DIALOG] = useDialog } }
    }

    companion object {
        private val THEME_KEY = intPreferencesKey("theme")
        private val USE_DIFFICULTY_DIALOG = booleanPreferencesKey("use_difficulty_dialog")
    }
}