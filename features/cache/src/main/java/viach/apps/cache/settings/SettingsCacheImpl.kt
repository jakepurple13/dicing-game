package viach.apps.cache.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import viach.apps.cache.SavedGame
import viach.apps.cache.extensions.savedGames
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

class SavedGameCacheImpl(
    context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : SavedGameCache {
    private val preferences: DataStore<SavedGame> = context.savedGames

    override val all: Flow<SavedGame> get() = preferences.data
    override var currentData: SavedGame = SavedGame.getDefaultInstance()

    init {
        coroutineScope.launch {
            all.collectLatest { data ->
                currentData = data
            }
        }
    }

    override fun update(statsBuilder: suspend SavedGame.Builder.() -> SavedGame.Builder): Job = coroutineScope.launch {
        preferences.updateData { statsBuilder(it.toBuilder()).build() }
    }

    override var playerOneField: List<Int>
        get() = currentData.playerOneFieldList
        set(value) {
            update {
                clearPlayerOneField()
                addAllPlayerOneField(value)
            }
        }

    override var playerTwoField: List<Int>
        get() = currentData.playerTwoFieldList
        set(value) {
            update {
                clearPlayerTwoField()
                addAllPlayerTwoField(value)
            }
        }

    override var difficulty: String
        get() = currentData.aiDifficulty
        set(value) {
            update { setAiDifficulty(value) }
        }

    override var savedGame: Boolean
        get() = currentData.hasSavedGame
        set(value) {
            update { setHasSavedGame(value) }
        }

    override var turn: Int
        get() = currentData.turn
        set(value) {
            update { setTurn(value) }
        }

    override var nextDice: Int
        get() = currentData.nextDice
        set(value) {
            update { setNextDice(value) }
        }

    override fun hasSavedGame() = all.map { it.hasSavedGame }

    override fun clearGame() {
        update {
            clear()
        }
    }
}
