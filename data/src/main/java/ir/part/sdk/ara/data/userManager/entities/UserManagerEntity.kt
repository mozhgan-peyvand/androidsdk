package ir.part.sdk.ara.data.userManager.entities

import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class UserManagerEntity(
    var nationalCode: String,
    var token: String,
    var cookie: String,
    var mobilePhone: String
) {
}