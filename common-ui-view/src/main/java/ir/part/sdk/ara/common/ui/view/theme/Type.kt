package ir.part.sdk.ara.common.ui.view.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.part.sdk.ara.common.ui.view.*


val Typography = Typography(
    defaultFontFamily = FontFamily(
        Font(R.font.iran_yekan_bold, weight = FontWeight.Bold),
        Font(R.font.iran_yekan_regular, weight = FontWeight.Normal)
    ),
    h4 = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    ),
    h5 = TextStyle(
        fontSize = 23.sp,
        fontWeight = FontWeight.Bold
    ),
    h6 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    subtitle1 = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold
    ),
    subtitle2 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    body1 = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal
    ),
    body2 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    button = TextStyle(
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
)

@Composable
fun Typography.subtitle1TextPrimary(): TextStyle {
    return Typography.subtitle1.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Normal

    )
}

@Composable
fun Typography.subtitle1TextSecondary(): TextStyle {
    return Typography.subtitle1.copy(
        color = MaterialTheme.colors.textSecondary(),
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun Typography.subtitle1TextPrimaryBold(): TextStyle {
    return Typography.subtitle1.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.subtitle1TextHighlight(): TextStyle {
    return Typography.subtitle1.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.subtitle2TextPrimary(): TextStyle {
    return Typography.subtitle2.copy(
        color = MaterialTheme.colors.textPrimary()
    )
}

@Composable
fun Typography.body2TextPrimaryBold(): TextStyle {
    return Typography.body2.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.body2TextSecondary(): TextStyle {
    return Typography.body2.copy(
        color = MaterialTheme.colors.textSecondary(),
    )
}


@Composable
fun Typography.body2TextSecondaryBold(): TextStyle {
    return Typography.body2.copy(
        color = MaterialTheme.colors.textSecondary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.body2Highlight(): TextStyle {
    return Typography.body2.copy(
        color = ColorBlueDarker
    )
}

@Composable
fun Typography.subtitle2Error(): TextStyle {
    return Typography.subtitle2.copy(
        color = MaterialTheme.colors.error
    )
}

@Composable
fun Typography.subtitle2TextSecondary(): TextStyle {
    return Typography.subtitle2.copy(
        color = MaterialTheme.colors.textSecondary()
    )
}

@Composable
fun Typography.subtitle2Highlight(): TextStyle {
    return Typography.subtitle2.copy(
        color = MaterialTheme.colors.primaryVariant()
    )
}

@Composable
fun Typography.captionPrimary(): TextStyle {
    return Typography.caption.copy(
        color = MaterialTheme.colors.textPrimary()
    )
}

@Composable
fun Typography.captionPrimaryBold(): TextStyle {
    return Typography.caption.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.captionPrimaryBoldSuccess(): TextStyle {
    return Typography.caption.copy(
        color = MaterialTheme.colors.success(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.captionError(): TextStyle {
    return Typography.caption.copy(
        color = MaterialTheme.colors.error
    )
}

@Composable
fun Typography.h5Bold(): TextStyle {
    return Typography.h5.copy(
        fontWeight = FontWeight.Bold,
        color = ColorBlueDarker
    )
}

@Composable
fun Typography.h5Highlight(): TextStyle {
    return Typography.h5.copy(
        color = ColorBlueDarker
    )
}

@Composable
fun Typography.h5BoldPrimary(): TextStyle {
    return Typography.h5.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun Typography.h6Bold(): TextStyle {
    return Typography.h6.copy(
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Typography.h6BoldPrimary(): TextStyle {
    return Typography.h6.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.textPrimary()
    )
}

@Composable
fun Typography.outlinedButtonPrimaryNormal(): TextStyle {
    return Typography.body2.copy(
        fontWeight = FontWeight.Bold,
        color = ColorBlueDarker
    )
}

@Composable
fun Typography.body2Primary(): TextStyle {
    return Typography.body2.copy(
        color = MaterialTheme.colors.textPrimary()
    )
}

@Composable
fun Typography.subtitle2PrimaryBold(): TextStyle {
    return Typography.subtitle2.copy(
        fontWeight = FontWeight.Bold,
        color = ColorGrayDarker
    )
}

@Composable
fun Typography.caption_Secondary(): TextStyle {
    return Typography.caption.copy(
        color = ColorGray
    )
}

@Composable
fun Typography.h5OnPrimary(): TextStyle {
    return Typography.h5.copy(
        color = MaterialTheme.colors.onPrimary
    )
}

@Composable
fun Typography.body2PrimaryBold(): TextStyle {
    return Typography.body2.copy(
        color = MaterialTheme.colors.textPrimary(), fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.body2Secondary(): TextStyle {
    return Typography.body2.copy(
        color = MaterialTheme.colors.textSecondary()
    )
}

@Composable
fun Typography.body2SecondaryBold(): TextStyle {
    return Typography.body2.copy(
        color = MaterialTheme.colors.textSecondary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.subtitle2TextPrimaryBold(): TextStyle {
    return Typography.subtitle2.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun Typography.buttonTextStyle(): TextStyle {
    return Typography.button.copy(
        color = MaterialTheme.colors.onPrimary
    )
}

@Composable
fun Typography.buttonTextPrimaryVariantStyle(): TextStyle {
    return Typography.button.copy(
        color = MaterialTheme.colors.primaryVariant()
    )
}

