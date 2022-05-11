package ir.part.sdk.ara.builder.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.part.sdk.ara.builder.R

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
fun Typography.subtitle2TextPrimaryBold(): TextStyle {
    return Typography.subtitle2.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}