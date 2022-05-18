package ir.part.sdk.ara.common.ui.view.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable

@Composable
fun AraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalRippleTheme provides SecondaryRippleTheme
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }
}

private val DarkColorPalette = darkColors(
    primary = ColorEastSide,
    primaryVariant = ColorHavelockBlue,
    onPrimary = ColorWhite,
    background = ColorCodGray,
    surface = ColorCodGray,
    error = ColorChestnutRose,
)

private val LightColorPalette = lightColors(
    primary = ColorBlue,
    primaryVariant = ColorBlueDarker,
    onPrimary = ColorWhite,
    background = ColorWhite,
    surface = ColorWhite,
    error = ColorRed,
)

@Immutable
object SecondaryRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = MaterialTheme.colors.secondary,
        lightTheme = MaterialTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = MaterialTheme.colors.secondary,
        lightTheme = MaterialTheme.colors.isLight
    )
}