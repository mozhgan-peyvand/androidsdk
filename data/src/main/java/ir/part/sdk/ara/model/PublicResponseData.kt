package ir.part.sdk.ara.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PublicResponseData<out T>(
    val results: List<T>
)