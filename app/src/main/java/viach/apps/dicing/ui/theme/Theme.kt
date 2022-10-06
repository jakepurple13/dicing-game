package viach.apps.dicing.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

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
    CompositionLocalProvider(
        LocalThemeSpacing provides remember { Spacing() }
    ) {
        MaterialTheme(
            colors = theme.getTheme(darkTheme).let { colors ->
                colors.copy(
                    primary = animateColorAsState(colors.primary).value,
                    primaryVariant = animateColorAsState(colors.primaryVariant).value,
                    onPrimary = animateColorAsState(colors.onPrimary).value,
                    secondary = animateColorAsState(colors.secondary).value,
                    secondaryVariant = animateColorAsState(colors.secondaryVariant).value,
                    onSecondary = animateColorAsState(colors.onSecondary).value,
                    background = animateColorAsState(colors.background).value,
                    onBackground = animateColorAsState(colors.onBackground).value,
                    surface = animateColorAsState(colors.surface).value,
                    onSurface = animateColorAsState(colors.onSurface).value,
                    onError = animateColorAsState(colors.onError).value,
                    error = animateColorAsState(colors.error).value,
                    isLight = colors.isLight
                )
            },
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
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