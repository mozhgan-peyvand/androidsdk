package ir.part.sdk.ara.ui.user.changePassword

import ir.part.sdk.ara.domain.user.entities.ChangePasswordParam

data class ChangePasswordView(
    val currentPassword: String,
    val newPassword: String,
    val renewPassword: String
){
    fun toChangePasswordParam() = ChangePasswordParam(
        currentPassword = currentPassword,
        newPassword = newPassword,
        renewPassword = renewPassword
    )
}