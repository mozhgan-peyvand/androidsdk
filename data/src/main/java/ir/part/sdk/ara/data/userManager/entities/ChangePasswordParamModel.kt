package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ChangePasswordParamModel(
    val currentPassword: String,
    val newPassword: String,
    val renewPassword: String
)

