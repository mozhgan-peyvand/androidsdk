package ir.part.sdk.ara.common.ui.view.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

fun Colors.primaryDark() =
    CustomAttributes.primaryDark
        ?: kotlin.run { if (isLight) ColorBlueDarker2 else ColorMineShaft } // todo: dark?

fun Colors.errorBackground() =
    CustomAttributes.errorBackground
        ?: kotlin.run { if (isLight) ColorRedLight else ColorBlue }// todo: dark?}

fun Colors.success() =
    CustomAttributes.success ?: kotlin.run { if (isLight) ColorGreen else ColorBlue }// todo: dark?}

fun Colors.dialogBackground() =
    CustomAttributes.dialogBackground
        ?: kotlin.run { if (isLight) ColorWhite else ColorBlue }// todo: dark?}

fun Colors.successBackground() =
    CustomAttributes.successBackground
        ?: kotlin.run { if (isLight) ColorGreenLight else ColorBlue }// todo: dark?}

fun Colors.textPrimary() =
    CustomAttributes.textPrimary
        ?: kotlin.run { if (isLight) ColorGrayDarker else ColorWhite }// todo: dark?}

fun Colors.textSecondary() =
    CustomAttributes.textSecondary
        ?: kotlin.run { if (isLight) ColorGray else ColorMartini }// todo: dark?}

fun Colors.divider() =
    CustomAttributes.divider
        ?: kotlin.run { if (isLight) ColorGrayLighter else ColorBlue }// todo: dark?}

fun Colors.disabled() =
    CustomAttributes.disabled
        ?: kotlin.run { if (isLight) ColorGray else ColorBlue }// todo: dark?}

fun Colors.statusBar() =
    CustomAttributes.statusBar
        ?: kotlin.run { if (isLight) ColorWhite else ColorBlue }// todo: dark?}

//fun Colors.accent() =
//    CustomAttributes.accent ?: kotlin.run { if (isLight) ColorCyan else ColorBlue }// todo: dark?}

fun Colors.highlightBackground() =
    CustomAttributes.highlightBackground
        ?: kotlin.run { if (isLight) ColorBlueLight else ColorBlue }// todo: dark?}

fun Colors.textHighlight() = CustomAttributes.textHighlight ?: kotlin.run { primaryDark() }
fun Colors.controlNormal() = CustomAttributes.controlNormal ?: kotlin.run { textSecondary() }
fun Colors.controlActivated() = CustomAttributes.controlActivated ?: kotlin.run { primaryVariant }
fun Colors.controlHighlight() = CustomAttributes.controlHighlight ?: kotlin.run { primary }


/**
 * Only for customers to change
 * */
object CustomAttributes {
    var primaryDark: Color? = null
    var errorBackground: Color? = null
    var success: Color? = null
    var dialogBackground: Color? = null
    var successBackground: Color? = null
    var textPrimary: Color? = null
    var textSecondary: Color? = null
    var divider: Color? = null
    var statusBar: Color? = null
    var accent: Color? = null
    var highlightBackground: Color? = null
    var textHighlight: Color? = null
    var controlNormal: Color? = null
    var controlActivated: Color? = null
    var controlHighlight: Color? = null
    var disabled: Color? = null
    var primary: Color? = null
    var primaryVariant: Color? = null
    var secondary: Color? = null
    var secondaryVariant: Color? = null
    var background: Color? = null
    var surface: Color? = null
    var error: Color? = null
    var onPrimary: Color? = null
    var onSecondary: Color? = null
    var onBackground: Color? = null
    var onSurface: Color? = null
    var onError: Color? = null
}