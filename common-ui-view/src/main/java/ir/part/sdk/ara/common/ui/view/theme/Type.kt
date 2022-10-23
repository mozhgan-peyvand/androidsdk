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
fun Typography.h5PrimaryVariant(): TextStyle {
    return h5.copy(
        color = MaterialTheme.colors.primaryVariant()
    )
}

@Composable
fun Typography.h5BoldTextPrimary(): TextStyle {
    return h5.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.h6OnPrimary(): TextStyle {
    return h6.copy(
        color = MaterialTheme.colors.onPrimary()
    )
}

@Composable
fun Typography.h6Bold(): TextStyle {
    return h6.copy(
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Typography.h6BoldTextPrimary(): TextStyle {
    return h6.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.subtitle1TextPrimary(): TextStyle {
    return subtitle1.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun Typography.subtitle1TextSecondary(): TextStyle {
    return subtitle1.copy(
        color = MaterialTheme.colors.textSecondary(),
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun Typography.subtitle1BoldTextPrimary(): TextStyle {
    return subtitle1.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.subtitle2TextSecondary(): TextStyle {
    return subtitle2.copy(
        color = MaterialTheme.colors.textSecondary()
    )
}

@Composable
fun Typography.subtitle2PrimaryVariant(): TextStyle {
    return subtitle2.copy(
        color = MaterialTheme.colors.primaryVariant()
    )
}

@Composable
fun Typography.subtitle2TextPrimary(): TextStyle {
    return subtitle2.copy(
        color = MaterialTheme.colors.textPrimary()
    )
}

@Composable
fun Typography.subtitle2BoldTextPrimary(): TextStyle {
    return subtitle2.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.captionTextPrimary(): TextStyle {
    return caption.copy(
        color = MaterialTheme.colors.textPrimary()
    )
}

@Composable
fun Typography.captionBoldTextPrimary(): TextStyle {
    return caption.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.captionBoldSuccess(): TextStyle {
    return caption.copy(
        color = MaterialTheme.colors.success(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.captionTextSecondary(): TextStyle {
    return caption.copy(
        color = MaterialTheme.colors.textSecondary()
    )
}

@Composable
fun Typography.body2BoldTextPrimary(): TextStyle {
    return body2.copy(
        color = MaterialTheme.colors.textPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.body2TextSecondary(): TextStyle {
    return body2.copy(
        color = MaterialTheme.colors.textSecondary()
    )
}

@Composable
fun Typography.body2OnPrimary(): TextStyle {
    return body2.copy(
        color = MaterialTheme.colors.onPrimary(),
    )
}

@Composable
fun Typography.body2BoldOnPrimary(): TextStyle {
    return body2.copy(
        color = MaterialTheme.colors.onPrimary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.body2BoldTextSecondary(): TextStyle {
    return body2.copy(
        color = MaterialTheme.colors.textSecondary(),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Typography.body2PrimaryVariant(): TextStyle {
    return body2.copy(
        color = MaterialTheme.colors.primaryVariant()
    )
}

@Composable
fun Typography.body2TextPrimary(): TextStyle {
    return body2.copy(
        color = MaterialTheme.colors.textPrimary()
    )
}

@Composable
fun Typography.buttonTextStyle(): TextStyle {
    return button.copy(
        color = MaterialTheme.colors.onPrimary()
    )
}

@Composable
fun Typography.buttonTextPrimaryVariantStyle(): TextStyle {
    return button.copy(
        color = MaterialTheme.colors.primaryVariant()
    )
}