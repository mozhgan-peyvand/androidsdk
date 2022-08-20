package ir.part.sdk.ara.data.barjavand.entities

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class BarjavandResultEntity<out T>(
    val data: T
)
