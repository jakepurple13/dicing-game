package viach.apps.dicing.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Brown500 = Color(0xff795548)
val BrownDark500 = Color(0xff4b2c20)
val AmberLight700a = Color(0xffffdd4b)
val Amber700a = Color(0xffffab00)

val GoldPrimary = Color(0xFFCCCC57)
val GoldPrimaryDark = Color(0xFFE8E870)
val BlueBackground = Color(0xff1976d2)
val BlueBackgroundDark = Color(0xff0069c0)

@Composable
fun Color.animate() = animateColorAsState(this)