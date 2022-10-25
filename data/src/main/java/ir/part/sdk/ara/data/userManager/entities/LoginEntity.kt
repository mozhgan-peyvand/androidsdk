package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class LoginEntity(
    var token: String,
    var roles: List<String>,
    var refreshToken: String,
    var type: String,
    var uniqueKey: String,
    var cellphoneNumbers: List<CellPhone>,
    var email: String?
)

@JsonSerializable
data class CellPhone(
    val id: String,
    val value: String
)