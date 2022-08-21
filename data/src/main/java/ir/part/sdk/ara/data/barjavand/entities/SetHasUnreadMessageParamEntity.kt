package ir.part.sdk.ara.data.barjavand.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class SetHasUnreadMessageParamEntity(
    val schema: Schema,
    val id: String,
    val data: SetHasUnreadMessageParamEntityData
)

@JsonSerializable
data class SetHasUnreadMessageParamEntityData(
    val hasUnreadMessage: Boolean
)