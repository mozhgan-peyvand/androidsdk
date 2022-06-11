package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class RegisterParamModel(
    val username: String? = null,
    val cellphoneNumbers: List<CellPhoneNumbers>,
    val email: String? = null,
    val captcha: CaptchaModel? = null
)

@JsonSerializable
data class CellPhoneNumbers(
    val value: String? = null
)

@JsonSerializable
data class RegisterResponseNetwork(
    val userId: String
)