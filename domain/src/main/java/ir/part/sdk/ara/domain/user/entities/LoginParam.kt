package ir.part.sdk.ara.domain.user.entities

data class LoginParam(
    val nationalCode: String,
    val password: String,
    val rememberMe: Boolean = false,
    val captchaValue: String? = null,
    val captchaToken: String? = null
)
