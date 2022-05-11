package ir.part.sdk.ara.domain.user.entities

data class ChangePasswordParam(
    val currentPassword: String,
    val newPassword: String,
    val renewPassword: String
)
