package viach.apps.cache.settings

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import viach.apps.cache.SavedGame

interface SettingsCache {
    val all: Flow<Preferences>
    val theme: Flow<Int>
    fun setTheme(themeIndex: Int)
    val useDifficultyDialog: Flow<Boolean>
    fun setUseDifficultyDialog(useDialog: Boolean)
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