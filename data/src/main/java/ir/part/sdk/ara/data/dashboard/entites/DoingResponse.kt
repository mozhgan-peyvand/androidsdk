package ir.part.sdk.ara.data.dashboard.entites

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class DoingResponse(
    @Json(name = "date") var date: String? = null,
    @Json(name = "id") var id: String? = null,
    @Json(name = "type") var type: String? = null,
    @Json(name = "taskId") var taskId: String? = null,
    @Json(name = "dashboardId") var dashboardId: String? = null,
    @Json(name = "actorId") var actorId: String? = null,
    @Json(name = "actorRole") var actorRole: List<String> = listOf(),
    @Json(name = "state") var state: Boolean? = null,
    @Json(name = "tags") var tags: List<String> = listOf(),
    @Json(name = "processInstanceId") var processInstanceId: String? = null,
    @Json(name = "labels") var labels: List<String> = listOf()
)
