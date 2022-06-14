package ir.part.sdk.ara.domain.user.entities

data class RegisterParam(
    val nationalCode: String? = null,
    val mobile: String? = null,
    val email: String? = null,
    val captchaValue: String? = null,
    val captchaToken: String? = null
)