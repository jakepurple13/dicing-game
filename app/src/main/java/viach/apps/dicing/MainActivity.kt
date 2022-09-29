package viach.apps.dicing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import viach.apps.dicing.ui.theme.DicingTheme
import viach.apps.dicing.ui.view.screen.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DicingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // Remember a SystemUiController
                    val systemUiController = rememberSystemUiController()

                    val background = MaterialTheme.colors.background

                    DisposableEffect(systemUiController) {
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