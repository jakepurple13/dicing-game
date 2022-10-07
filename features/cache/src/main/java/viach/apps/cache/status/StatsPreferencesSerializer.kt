package viach.apps.cache.status

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.GeneratedMessageLite
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import viach.apps.cache.SavedGame
import viach.apps.cache.SettingsPreferences
import viach.apps.cache.StatsPreferences
import java.io.InputStream
import java.io.OutputStream

//Keeping this for reference
/*object StatsPreferencesSerializer : Serializer<StatsPreferences> {
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
}*/

object StatsPreferencesSerializer : GenericSerializer<StatsPreferences, StatsPreferences.Builder> {
    override val defaultValue: StatsPreferences get() = StatsPreferences.getDefaultInstance()
    override val parseFrom: (input: InputStream) -> StatsPreferences get() = StatsPreferences::parseFrom
}

object SavedGameSerializer : GenericSerializer<SavedGame, SavedGame.Builder> {
    override val defaultValue: SavedGame get() = SavedGame.getDefaultInstance()
    override val parseFrom: (input: InputStream) -> SavedGame get() = SavedGame::parseFrom
}

object SettingsPreferencesSerializer : GenericSerializer<SettingsPreferences, SettingsPreferences.Builder> {
    override val defaultValue: SettingsPreferences get() = SettingsPreferences.getDefaultInstance()
    override val parseFrom: (input: InputStream) -> SettingsPreferences get() = SettingsPreferences::parseFrom
}

interface GenericSerializer<MessageType, BuilderType> : Serializer<MessageType>
        where MessageType : GeneratedMessageLite<MessageType, BuilderType>,
              BuilderType : GeneratedMessageLite.Builder<MessageType, BuilderType> {

    /**
     * Call MessageType::parseFrom here!
     */
    val parseFrom: (input: InputStream) -> MessageType

    override suspend fun readFrom(input: InputStream): MessageType =
        withContext(Dispatchers.IO) {
            try {
                parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

    override suspend fun writeTo(t: MessageType, output: OutputStream) =
        withContext(Dispatchers.IO) { t.writeTo(output) }
}
