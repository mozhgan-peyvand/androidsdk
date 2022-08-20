package ir.part.sdk.ara.ui.document.submitDocument.model

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import ir.part.sdk.ara.common.ui.view.*
import ir.part.sdk.ara.ui.document.R


enum class DocumentsStatusView(val value: String, val code: Float) {

    CODE_11("11", 11F),
    CODE_11_3("11.3", 11.3F),
    CODE_11_6("11.6", 11.6F),
    CODE_12("12", 12F),
    CODE_12_3("12.3", 12.3F),
    CODE_12_6("12.6", 12.6F),
    CODE_13("13", 13F),
    CODE_14("14", 14F),
    CODE_18("18", 18F),
    CODE_21("21", 21F),
    CODE_31("31", 31F),
    CODE_100("100", 100F),
    CODE_200("200", 200F),
    CODE_300("300", 300F),
    CODE_400("400", 400F);

    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            CODE_11 -> MaterialTheme.colors.primaryVariant()
            CODE_11_3 -> MaterialTheme.colors.primaryVariant()
            CODE_11_6 -> MaterialTheme.colors.primaryVariant()
            CODE_12 -> MaterialTheme.colors.primaryVariant()
            CODE_12_3 -> MaterialTheme.colors.primaryVariant()
            CODE_12_6 -> MaterialTheme.colors.primaryVariant()
            CODE_13 -> MaterialTheme.colors.primaryVariant()
            CODE_14 -> MaterialTheme.colors.primaryVariant()
            CODE_18 -> MaterialTheme.colors.primaryVariant()
            CODE_21 -> MaterialTheme.colors.primaryVariant()
            CODE_31 -> MaterialTheme.colors.success()
            CODE_100 -> MaterialTheme.colors.error()
            CODE_200 -> MaterialTheme.colors.error()
            CODE_300 -> MaterialTheme.colors.error()
            CODE_400 -> MaterialTheme.colors.error()
        }

    val backgroundColor: Color
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            CODE_11 -> MaterialTheme.colors.highlightBackground()
            CODE_11_3 -> MaterialTheme.colors.highlightBackground()
            CODE_11_6 -> MaterialTheme.colors.highlightBackground()
            CODE_12 -> MaterialTheme.colors.highlightBackground()
            CODE_12_3 -> MaterialTheme.colors.highlightBackground()
            CODE_12_6 -> MaterialTheme.colors.highlightBackground()
            CODE_13 -> MaterialTheme.colors.highlightBackground()
            CODE_14 -> MaterialTheme.colors.highlightBackground()
            CODE_18 -> MaterialTheme.colors.highlightBackground()
            CODE_21 -> MaterialTheme.colors.highlightBackground()
            CODE_31 -> MaterialTheme.colors.successBackground()
            CODE_100 -> MaterialTheme.colors.errorBackground()
            CODE_200 -> MaterialTheme.colors.errorBackground()
            CODE_300 -> MaterialTheme.colors.errorBackground()
            CODE_400 -> MaterialTheme.colors.errorBackground()
        }

    val icon: Painter
        @Composable
        get() = when (this) {
            CODE_11 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_11_3 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_11_6 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_12 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_12_3 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_12_6 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_13 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_14 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_18 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_21 -> painterResource(R.drawable.ara_ic_hourglass)
            CODE_31 -> painterResource(R.drawable.merat_ic_i_check)
            CODE_100 -> painterResource(R.drawable.merat_ic_s_warning)
            CODE_200 -> painterResource(R.drawable.merat_ic_s_warning)
            CODE_300 -> painterResource(R.drawable.merat_ic_s_warning)
            CODE_400 -> painterResource(R.drawable.merat_ic_s_warning)
        }
}