package ir.part.sdk.ara.domain.menu.entities

data class BodyComment(
    val captcha: Captcha,
    val data: DataComment,
)

data class Captcha(
    val token: String,
    val value: String,
)

data class DataComment(
    val description: String,
    val email: String,
    val family: String,
    val mobile: String,
    val name: String,
)