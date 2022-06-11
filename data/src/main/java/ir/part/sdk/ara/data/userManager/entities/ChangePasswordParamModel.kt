package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ChangePasswordParamModel(
    var oldAuthenticateExtra: OldAuthenticateExtra,
    val password: String,
)

@JsonSerializable
data class OldAuthenticateExtra(
var password : String
)

