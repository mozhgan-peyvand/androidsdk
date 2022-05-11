package ir.part.sdk.ara.data.userManager.entities

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Base64
import ir.part.sdk.ara.domain.user.entities.Captcha
import ir.part.sdk.ara.util.androidsvg.SVG

import se.ansman.kotshi.JsonSerializable
import java.nio.charset.Charset
import kotlin.math.ceil

@JsonSerializable
data class CaptchaEntity(
    var img: String? = null,
    var token: String? = null,
    var version: String? = null
) {
    private fun imageFromString(imageData: String?): Bitmap {
        val data = imageData!!.substring(imageData.indexOf(",") + 1)
        val imageAsBytes = Base64.decode(data.toByteArray(), Base64.DEFAULT)
        val svgAsString = String(imageAsBytes, Charset.forName("UTF-8"))

        val svg = SVG.getFromString(svgAsString)

        // Create a bitmap and canvas to draw onto
        val svgWidth = if (svg.documentWidth !== (-1).toFloat()) svg.documentWidth else 500f
        val svgHeight = if (svg.documentHeight !== (-1).toFloat()) svg.documentHeight else 500f

        val newBM = Bitmap.createBitmap(
            ceil(svgWidth.toDouble()).toInt(),
            ceil(svgHeight.toDouble()).toInt(),
            Bitmap.Config.ARGB_8888
        )
        val bmcanvas = Canvas(newBM)

        // Clear background to white if you want
        bmcanvas.drawRGB(255, 255, 255)

        // Render our document onto our canvas
        svg.renderToCanvas(bmcanvas)

        return newBM
    }

    fun toCaptcha() = Captcha(
        img = imageFromString(img),
        token = token,
        version = version,
    )

}