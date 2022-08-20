package ir.part.sdk.ara.data.barjavand.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Schema(
    val name: String,
    val version: String
)
