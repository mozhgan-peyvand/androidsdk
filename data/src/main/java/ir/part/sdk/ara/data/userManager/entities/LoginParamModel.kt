package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class LoginParamModel(
    val username: String,
    val password: String,
    val captcha: CaptchaModel
)

@JsonSerializable
data class CaptchaModel(
    val value: String? = null,
    val token: String? = null
)