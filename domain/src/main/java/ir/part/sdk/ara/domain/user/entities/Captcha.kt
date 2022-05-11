package ir.part.sdk.ara.domain.user.entities

import android.graphics.Bitmap

data class Captcha(
    val img: Bitmap,
    val token: String? = null,
    val version: String? = null
)