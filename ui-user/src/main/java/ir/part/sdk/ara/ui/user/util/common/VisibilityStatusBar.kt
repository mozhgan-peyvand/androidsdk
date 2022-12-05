package ir.part.sdk.ara.ui.user.util.common

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import ir.part.app.merat.ui.user.R
import ir.part.sdk.ara.common.ui.view.statusBar

@Composable
fun VisibilityStatusBar(state: Boolean) {

    if (!state) {
        (LocalContext.current as Activity).window.statusBarColor =
            MaterialTheme.colors.statusBar().toArgb()
        (LocalContext.current as Activity).window.setFlags(
            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        (LocalContext.current as Activity).window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (LocalContext.current as Activity).window.statusBarColor =
                ContextCompat.getColor(LocalContext.current, R.color.commonResourceColorBackground)
        }
    } else {
        (LocalContext.current as Activity).window.statusBarColor =
            Color.Transparent.toArgb()
        (LocalContext.current as Activity).window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

}