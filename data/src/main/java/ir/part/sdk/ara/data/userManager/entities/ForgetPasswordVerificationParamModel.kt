package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ForgetPasswordVerificationParamModel(
    val username: String,
    val token: String
)

