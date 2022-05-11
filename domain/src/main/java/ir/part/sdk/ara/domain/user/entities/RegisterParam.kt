package ir.part.sdk.ara.domain.user.entities

data class RegisterParam(
    val nationalCode: String,
    val mobile: String,
    val email: String?,
    val captchaValue: String? = null,
    val captchaToken: String? = null
)