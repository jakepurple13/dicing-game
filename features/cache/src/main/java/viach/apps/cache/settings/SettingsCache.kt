package viach.apps.cache.settings

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import viach.apps.cache.SavedGame
import viach.apps.cache.SettingsPreferences
import viach.apps.cache.SystemThemeMode

interface SettingsCache {
    var currentData: SettingsPreferences
    val all: Flow<SettingsPreferences>
    val theme: Flow<Int>
    fun setTheme(themeIndex: Int)
    val useDifficultyDialog: Flow<Boolean>
    fun setUseDifficultyDialog(useDialog: Boolean)
    fun update(statsBuilder: suspend SettingsPreferences.Builder.() -> SettingsPreferences.Builder): Job
    val themeMode: Flow<SystemThemeMode>
    fun setThemeMode(mode: SystemThemeMode)
}

interface SavedGameCache {
    val all: Flow<SavedGame>
    var currentData: SavedGame
    fun update(statsBuilder: suspend SavedGame.Builder.() -> SavedGame.Builder): Job

    var playerOneField: List<Int>
    var playerTwoField: List<Int>
    var difficulty: String
    var savedGame: Boolean
    var turn: Int
    var nextDice: Int

    fun hasSavedGame(): Flow<Boolean>

    fun clearGame()
}