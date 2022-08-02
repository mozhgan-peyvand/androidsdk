package ir.part.sdk.ara.common.ui.view.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.part.sdk.ara.common.ui.view.R


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
)

@Composable
fun Typography.subtitle1TextPrimary(): TextStyle {
    return Typography.subtitle1.copy(
        color = MaterialTheme.colors.textPrimary()
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
fun Typography.subtitle2TextPrimary(): TextStyle {
    return Typography.subtitle2.copy(
        color = MaterialTheme.colors.textPrimary()
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
        color = MaterialTheme.colors.primaryDark()
    )
}

@Composable
fun Typography.captionPrimary(): TextStyle {
    return Typography.caption.copy(
        color = MaterialTheme.colors.textPrimary()
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
fun Typography.h6Bold(): TextStyle {
    return Typography.h6.copy(
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Typography.meratOutlinedButton_Primary_Normal(): TextStyle {
    return Typography.body2.copy(
        fontWeight = FontWeight.Bold,
        color = ColorBlueDarker
    )
}

@Composable
fun Typography.subtitle2_Primary_Bold(): TextStyle {
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