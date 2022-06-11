package ir.part.sdk.ara.ui.user.captcha

import android.graphics.Bitmap

data class CaptchaViewState(
    val img: Bitmap? = null,
    val token: String? = null,
    val version: String? = null,
)
