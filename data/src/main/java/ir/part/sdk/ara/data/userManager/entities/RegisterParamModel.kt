package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class RegisterParamModel(
    val username: String,
    val cellphoneNumbers: List<CellPhoneNumbers>,
    val email: String? = null,
//    TODO: is captcha needed?
//    val captcha: CaptchaModel
)

@JsonSerializable
data class CellPhoneNumbers(
    val value: String
)

@JsonSerializable
data class RegisterResponseNetwork(
    val userId: String
)