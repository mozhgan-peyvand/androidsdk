package ir.part.sdk.ara.data.barjavand.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class SetFlagEntity(
    val schemaName: String,
    val schemaVersion: String,
    val dataId: String,
    val data: ReadMessageEntity
)

@JsonSerializable
data class ReadMessageEntity(val hasUnreadMessage: Boolean)