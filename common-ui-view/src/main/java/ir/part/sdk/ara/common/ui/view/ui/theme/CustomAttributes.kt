package ir.part.sdk.ara.common.ui.view.ui.theme

import androidx.compose.material.Colors

fun Colors.primaryDark() = if (isLight) ColorBlueDarker2 else ColorMineShaft // todo: dark?
fun Colors.errorBackground() = if (isLight) ColorRedLight else ColorBlue // todo: dark?}
fun Colors.success() = if (isLight) ColorGreen else ColorBlue // todo: dark?}
fun Colors.dialogBackground() = if (isLight) ColorWhite else ColorBlue // todo: dark?}
fun Colors.successBackground() = if (isLight) ColorGreenLight else ColorBlue // todo: dark?}
fun Colors.textPrimary() = if (isLight) ColorGrayDarker else ColorWhite // todo: dark?}
fun Colors.textSecondary() = if (isLight) ColorGray else ColorMartini // todo: dark?}
fun Colors.divider() = if (isLight) ColorGrayLighter else ColorBlue // todo: dark?}
fun Colors.disabled() = if (isLight) ColorGray else ColorBlue // todo: dark?}
fun Colors.statusBar() = if (isLight) ColorWhite else ColorBlue // todo: dark?}
fun Colors.accent() = if (isLight) ColorCyan else ColorBlue // todo: dark?}
fun Colors.highlightBackground() = if (isLight) ColorBlueLight else ColorBlue // todo: dark?}
fun Colors.textHighlight() = primaryDark()
fun Colors.controlNormal() = textSecondary()
fun Colors.controlActivated() = primaryVariant
fun Colors.controlHighlight() = primary