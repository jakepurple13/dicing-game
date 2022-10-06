package viach.apps.cache.settings

import android.content.Context
import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import viach.apps.cache.SavedGame
import viach.apps.cache.SettingsPreferences
import viach.apps.cache.SystemThemeMode
import viach.apps.cache.extensions.savedGames
import viach.apps.cache.extensions.settingsPreferences

class SettingsCacheImpl(
    context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : SettingsCache {
    private val preferences: DataStore<SettingsPreferences> = context.settingsPreferences
    override val all: Flow<SettingsPreferences> get() = preferences.data
    override var currentData: SettingsPreferences = SettingsPreferences.getDefaultInstance()

    init {
        coroutineScope.launch {
            all.collectLatest { data ->
                currentData = data
            }
        }
    }

    override val theme: Flow<Int> get() = all.map { it.themeIndex }

    override fun setTheme(themeIndex: Int) {
        update { setThemeIndex(themeIndex) }
    }

    override val useDifficultyDialog: Flow<Boolean> get() = all.map { it.useDifficultyDialog }

    override fun setUseDifficultyDialog(useDialog: Boolean) {
        update { setUseDifficultyDialog(useDialog) }
    }

    override fun update(statsBuilder: suspend SettingsPreferences.Builder.() -> SettingsPreferences.Builder): Job =
        coroutineScope.launch {
            preferences.updateData { statsBuilder(it.toBuilder()).build() }
        }

    override val themeMode: Flow<SystemThemeMode> get() = all.map { it.mode }

    override fun setThemeMode(mode: SystemThemeMode) {
        update { setMode(mode) }
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
