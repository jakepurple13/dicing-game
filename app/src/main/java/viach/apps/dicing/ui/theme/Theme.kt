package viach.apps.dicing.ui.theme

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColors(
    primary = GoldPrimary,
    primaryVariant = GoldPrimaryDark,
    onPrimary = Color.Black,
    secondary = GoldPrimary,
    secondaryVariant = GoldPrimaryDark,
    onSecondary = Color.Black,
    background = BlueBackground,
    onBackground = Color.White,
    surface = BlueBackgroundDark,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Brown500,
    primaryVariant = BrownDark500,
    onPrimary = Color.White,
    secondary = Brown500,
    secondaryVariant = BrownDark500,
    onSecondary = Color.White,
    background = AmberLight700a,
    onBackground = Color.Black,
    surface = Amber700a,
    onSurface = Color.Black
)

private fun createLightColors(first: Color, second: Color, background: Color, surface: Color) = lightColors(
    primary = first,
    primaryVariant = second,
    onPrimary = Color.White,
    secondary = first,
    secondaryVariant = second,
    onSecondary = Color.White,
    background = background,
    onBackground = Color.Black,
    surface = surface,
    onSurface = Color.Black
)

private fun createDarkColors(first: Color, second: Color, background: Color, surface: Color) = darkColors(
    primary = first,
    primaryVariant = second,
    onPrimary = Color.Black,
    secondary = first,
    secondaryVariant = second,
    onSecondary = Color.Black,
    background = background,
    onBackground = Color.White,
    surface = surface,
    onSurface = Color.White
)

@Composable
fun DicingTheme(
    theme: Theme,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val isDarkTheme = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ||
            (isSystemInDarkTheme() && AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    CompositionLocalProvider(
        LocalThemeSpacing provides remember { Spacing() },
        LocalActivity provides remember { context.findActivity() }
    ) {
        MaterialTheme(
            colors = theme.getTheme(isDarkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

val LocalActivity = staticCompositionLocalOf<ComponentActivity> { error("Context is not an Activity.") }

fun Context.findActivity(): ComponentActivity {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is ComponentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    error("Context is not an Activity.")
}

@Suppress("unused")
val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalThemeSpacing.current

enum class Theme(private val light: Colors = LightColorPalette, private val dark: Colors = DarkColorPalette) {
    Default(LightColorPalette, LightColorPalette),
    Blue(DarkColorPalette, DarkColorPalette),
    NoColors(
        lightColors(
            primary = Color(0xff2196F3),
            secondary = Color(0xff90CAF9)
        ),
        darkColors(
            primary = Color(0xff90CAF9),
            secondary = Color(0xff90CAF9)
        )
    ),
    Red(
        createLightColors(
            first = Color(0xffbe0b07),
            second = Color(0xff705c2e),
            background = Color(0xffffdad5),
            surface = Color(0xfffbdfa6)
        ),
        createDarkColors(
            first = Color(0xffffb4a8),
            second = Color(0xffdec38c),
            background = Color(0xff930001),
            surface = Color(0xff564419)
        )
    );

    fun getTheme(darkTheme: Boolean) = if (darkTheme) dark else light
}