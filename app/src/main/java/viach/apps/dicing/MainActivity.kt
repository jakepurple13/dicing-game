package viach.apps.dicing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import viach.apps.cache.settings.SettingsCache
import viach.apps.dicing.ui.theme.DicingTheme
import viach.apps.dicing.ui.theme.Theme
import viach.apps.dicing.ui.view.screen.MainScreen

class MainActivity : ComponentActivity() {

    private val settingsCache: SettingsCache by inject()

    private val themeChoice by lazy {
        settingsCache.theme.map { index -> Theme.values().find { it.ordinal == index } ?: Theme.Default }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val defaultTheme = remember {
                runBlocking {
                    Theme.values().find { it.ordinal == (settingsCache.theme.firstOrNull() ?: 0) } ?: Theme.Default
                }
            }
            DicingTheme(theme = themeChoice.collectAsState(defaultTheme).value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // Remember a SystemUiController
                    val systemUiController = rememberSystemUiController()

                    val background = MaterialTheme.colors.background

                    DisposableEffect(systemUiController, background) {
                        // Update all of the system bar colors to be transparent, and use
                        // dark icons if we're in light theme
                        systemUiController.setSystemBarsColor(color = background)
                        // setStatusBarColor() and setNavigationBarColor() also exist
                        onDispose {}
                    }
                    MainScreen()
                }
            }
        }
    }
}