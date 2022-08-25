package ir.part.sdk.ara.domain.user.entities

data class ForgetPasswordParam(
    val nationalCode: String,
    val captchaValue: String? = null,
    val captchaToken: String? = null
)