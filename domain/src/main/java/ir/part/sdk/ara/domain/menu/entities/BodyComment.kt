package ir.part.sdk.ara.domain.menu.entities

data class BodyComment(
    val captchaToken: String,
    val captchaValue: String,
    val data: DataComment,
)

data class DataComment(
    val description: String,
    val email: String,
    val family: String,
    val mobile: String,
    val name: String,
)