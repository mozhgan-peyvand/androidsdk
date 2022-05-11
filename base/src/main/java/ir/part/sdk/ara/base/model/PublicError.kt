package ir.part.sdk.ara.base.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import se.ansman.kotshi.JsonSerializable

@JsonClass(generateAdapter = true)
@JsonSerializable
data class PublicError(
    var status: String? = null,
    var code: Int? = null,
    @field:Json(name = "error")
    var item: Error<Message>? = null
)
@JsonSerializable
@JsonClass(generateAdapter = true)
data class Error<T>(
    val message: T? = null
)
@JsonSerializable
@JsonClass(generateAdapter = true)
data class Message(
    val fa: String?,
    val en: String?
)
