package viach.apps.dicing.ui.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val xs: Dp = 2.dp,
    val s: Dp = 4.dp,
    val m: Dp = 8.dp,
    val l: Dp = 16.dp,
    val xl: Dp = 24.dp,
    val xxl: Dp = 32.dp
)

internal val LocalThemeSpacing: ProvidableCompositionLocal<Spacing> = staticCompositionLocalOf { Spacing() }