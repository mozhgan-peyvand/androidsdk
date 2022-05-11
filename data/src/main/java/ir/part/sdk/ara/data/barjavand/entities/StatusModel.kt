package ir.part.sdk.ara.data.barjavand.entities

import ir.part.sdk.ara.domain.document.entities.StatusParam
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class StatusModel(
    val name: String? = null,
    val title: String? = null
) {
    fun toStatusParam() = StatusParam(
        name = name,
        title = title
    )
}