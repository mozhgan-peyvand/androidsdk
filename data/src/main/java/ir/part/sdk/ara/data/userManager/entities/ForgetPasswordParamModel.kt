package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ForgetPasswordParamModel(
    val username: String,
    val captcha: CaptchaModel? = null
)

