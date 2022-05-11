package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class LoginEntity(
    var token: String,
    var roles: List<String>,
    var refreshToken: String,
    var type: String,
    var uniqueKey: String
)