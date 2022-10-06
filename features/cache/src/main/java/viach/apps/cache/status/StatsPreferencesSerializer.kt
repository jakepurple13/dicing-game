package viach.apps.cache.status

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import viach.apps.cache.SavedGame
import viach.apps.cache.SettingsPreferences
import viach.apps.cache.StatsPreferences
import java.io.InputStream
import java.io.OutputStream

object StatsPreferencesSerializer : Serializer<StatsPreferences> {
    override val defaultValue: StatsPreferences
        get() = StatsPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): StatsPreferences =
        withContext(Dispatchers.IO) {
            try {
                StatsPreferences.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

    override suspend fun writeTo(t: StatsPreferences, output: OutputStream) =
        withContext(Dispatchers.IO) {
            t.writeTo(output)
        }
}

object SavedGameSerializer : Serializer<SavedGame> {
    override val defaultValue: SavedGame
        get() = SavedGame.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SavedGame =
        withContext(Dispatchers.IO) {
            try {
                SavedGame.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

    override suspend fun writeTo(t: SavedGame, output: OutputStream) =
        withContext(Dispatchers.IO) {
            t.writeTo(output)
        }
}

object SettingsPreferencesSerializer : Serializer<SettingsPreferences> {
    override val defaultValue: SettingsPreferences
        get() = SettingsPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SettingsPreferences =
        withContext(Dispatchers.IO) {
            try {
                SettingsPreferences.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

    override suspend fun writeTo(t: SettingsPreferences, output: OutputStream) =
        withContext(Dispatchers.IO) {
            t.writeTo(output)
        }
}