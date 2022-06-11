package ir.part.sdk.ara.common.ui.view.theme

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
    primary = CustomAttributes.primary ?: ColorEastSide,
    primaryVariant = CustomAttributes.primaryVariant ?: ColorHavelockBlue,
    onPrimary = CustomAttributes.onPrimary ?: ColorWhite,
    background = CustomAttributes.background ?: ColorCodGray,
    surface = CustomAttributes.surface ?: ColorCodGray,
    error = CustomAttributes.error ?: ColorChestnutRose,
)

private val LightColorPalette = lightColors(
    primary = CustomAttributes.primary ?: ColorBlue,
    primaryVariant = CustomAttributes.primaryVariant ?: ColorBlueDarker,
    onPrimary = CustomAttributes.onPrimary ?: ColorWhite,
    background = CustomAttributes.background ?: ColorWhite,
    surface = CustomAttributes.surface ?: ColorWhite,
    error = CustomAttributes.error ?: ColorRed,
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