package ir.part.sdk.ara.data.state.entites

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseStateResponse(
    @field:Json(name = "processType")
    val processType: String? = null,
    @field:Json(name = "processId")
    val pid: String? = null,
    @field:Json(name = "piid")
    val processInstanceId: String? = null,
    @field:Json(name = "relatedPiids")
    val relatedProcessInstanceIds: List<String?> = listOf(),
    @field:Json(name = "tasks")
    val tasks: Map<String, Status>? = null
)


@JsonClass(generateAdapter = true)
data class Status(
    @field:Json(name = "status")
    val status: String?
)