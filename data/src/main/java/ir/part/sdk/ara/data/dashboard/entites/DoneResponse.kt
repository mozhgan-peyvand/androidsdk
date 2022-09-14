package ir.part.sdk.ara.data.dashboard.entites


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ir.part.sdk.ara.domain.tasks.entities.Done
import ir.part.sdk.ara.domain.tasks.entities.DoneInfo


@JsonClass(generateAdapter = true)
data class DoneResponse(
    @Json(name = "body")
    val body: DoneInfoEntity?,
    @Json(name = "keys")
    val keys: List<String?>?
) {
    fun toDone() = Done(
        body = body?.toDoneInfo(),
        keys = keys
    )
}

@JsonClass(generateAdapter = true)
data class DoneInfoEntity(
    @Json(name = "date")
    val date: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "processInstanceId")
    val processInstanceId: String?,
    @Json(name = "taskId")
    val taskId: String?,
    @Json(name = "dashboardId")
    val dashboardId: String?,
    @Json(name = "actorId")
    val actorId: String?,
    @Json(name = "actorRole")
    val actorRole: List<String?>,
    @Json(name = "state")
    val state: Boolean?
) {
    fun toDoneInfo() = DoneInfo(
        date = date,
        id = id,
        type = type,
        processInstanceId = processInstanceId,
        taskId = taskId,
        dashboardId = dashboardId,
        actorId = actorId,
        actorRole = actorRole,
        state = state
    )
}