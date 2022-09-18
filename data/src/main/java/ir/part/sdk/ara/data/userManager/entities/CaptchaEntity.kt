package ir.part.sdk.ara.data.userManager.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import ir.part.sdk.ara.domain.user.entities.Captcha
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class CaptchaEntity(
    var img: String? = null,
    var token: String? = null,
    var version: String? = null
) {
    private fun imageFromString(imageData: String?): Bitmap {
        val data = imageData!!.substring(imageData.indexOf(",") + 1)

        val decodedString: ByteArray = Base64.decode(
            data,
            Base64.CRLF
        )
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun toCaptcha() = Captcha(
        img = imageFromString(img),
        token = token,
        version = version,
    )

}