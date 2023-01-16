package ir.part.sdk.ara.data.state.entites

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class StateEntity(
    @field:Json(name = "searchToken")
    val searchToken: String? = null,
    @field:Json(name = "include")
    val include: List<String>? = listOf(),
    @field:Json(name = "sort")
    val sort: Boolean? = false,
    @field:Json(name = "sortMethod")
    val sortMethod: String? = null,
    @field:Json(name = "sortKeyIndex")
    val sortKeyIndex: Int? = 0,
    @field:Json(name = "pagination")
    val pagination: Boolean? = false,
    @field:Json(name = "pageSize")
    val pageSize: Int? = 0,
    @field:Json(name = "pageNumber")
    val pageNumber: Int? = 0
)