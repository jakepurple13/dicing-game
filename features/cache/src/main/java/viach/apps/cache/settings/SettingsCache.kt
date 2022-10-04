package viach.apps.cache.settings

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface SettingsCache {
    val all: Flow<Preferences>
    val theme: Flow<Int>
    fun setTheme(themeIndex: Int)
    val useDifficultyDialog: Flow<Boolean>
    fun setUseDifficultyDialog(useDialog: Boolean)
}