package ir.part.sdk.ara.data.dashboard.entites

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class DoneEntity(
    @field:Json(name = "taskName")
    val taskName: String? = null,
    @field:Json(name = "processInstanceId")
    val processInstanceId: String? = "",
    @field:Json(name = "taskId")
    val taskId: String? = null,
    @field:Json(name = "dashboardId")
    val dashboardId: String? = null,
    @field:Json(name = "actorId")
    val actorId: String? = null,
    @field:Json(name = "status")
    val status: String? = null,
    @field:Json(name = "state")
    val state: Boolean? = false
)