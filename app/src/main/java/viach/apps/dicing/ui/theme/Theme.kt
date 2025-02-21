package viach.apps.dicing.ui.theme

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
                    primary = colors.primary.animate().value,
                    primaryVariant = colors.primaryVariant.animate().value,
                    onPrimary = colors.onPrimary.animate().value,
                    secondary = colors.secondary.animate().value,
                    secondaryVariant = colors.secondaryVariant.animate().value,
                    onSecondary = colors.onSecondary.animate().value,
                    background = colors.background.animate().value,
                    onBackground = colors.onBackground.animate().value,
                    surface = colors.surface.animate().value,
                    onSurface = colors.onSurface.animate().value,
                    onError = colors.onError.animate().value,
                    error = colors.error.animate().value,
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

/**
 * I've been using [material theme builder](https://m3.material.io/theme-builder#/custom) and setting:
 * ```
 * First == Primary
 *
 * Background == PrimaryContainer
 *
 * Second == Secondary
 *
 * Surface == SecondaryContainer
 * ```
 * for both light and dark themes
 */
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
    ),
    DarkBlue(
        createLightColors(
            first = Color(0xff005db6),
            second = Color(0xff555f71),
            background = Color(0xffd6e3ff),
            surface = Color(0xffd9e3f9)
        ),
        createDarkColors(
            first = Color(0xffa9c7ff),
            second = Color(0xffbdc7dc),
            background = Color(0xff00468b),
            surface = Color(0xff3e4758)
        )
    ),
    Green(
        createLightColors(
            first = Color(0xff006c48),
            second = Color(0xff4d6356),
            background = Color(0xff8df7c2),
            surface = Color(0xffd0e8d8)
        ),
        createDarkColors(
            first = Color(0xff71dba7),
            second = Color(0xffb4ccbc),
            background = Color(0xff005235),
            surface = Color(0xff364b3f)
        )
    );

    fun getTheme(darkTheme: Boolean) = if (darkTheme) dark else light
}