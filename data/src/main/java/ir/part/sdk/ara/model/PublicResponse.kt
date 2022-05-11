package ir.part.sdk.ara.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PublicResponse<out T>(
    @field:Json(name = "data")
    val item: T? = null
)