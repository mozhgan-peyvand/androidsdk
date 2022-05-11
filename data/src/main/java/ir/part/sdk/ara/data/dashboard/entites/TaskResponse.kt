package ir.part.sdk.ara.data.dashboard.entites


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ir.part.sdk.ara.domain.tasks.entities.TaskInfo

@JsonClass(generateAdapter = true)
data class TaskResponse(
    @Json(name = "attributes")
    val attributes: Attributes?,
    @Json(name = "dashboardIds")
    val dashboardIds: List<String>?,
    @Json(name = "dataSchema")
    val dataSchema: DataSchema?,
    @Json(name = "duration")
    val duration: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "org")
    val org: String?,
    @Json(name = "processId")
    val processId: String?,
    @Json(name = "processInstanceId")
    val processInstanceId: String?,
    @Json(name = "stateSchema")
    val stateSchema: StateSchema?,
    @Json(name = "tags")
    val tags: List<String>?,
    @Json(name = "taskId")
    val taskId: String?,
    @Json(name = "type")
    val type: String?
) {
    fun toTaskInfo() = TaskInfo(
        taskId = taskId,
        name = name
    )
}

@JsonClass(generateAdapter = true)
data class Attributes(
    @Json(name = "actorUrl")
    val actorUrl: ActorUrl?,
    @Json(name = "config")
    val config: Any? = null,
    @Json(name = "subTasks")
    val subTasks: List<String>?
)
@JsonClass(generateAdapter = true)
data class DataSchema(
    @Json(name = "get")
    val `get`: Any? = null,
    @Json(name = "submit")
    val submit: Any? = null
)
@JsonClass(generateAdapter = true)
data class StateSchema(
    @Json(name = "type")
    val type: String?
)


@JsonClass(generateAdapter = true)
data class ActorUrl(
    @Json(name = "get")
    val `get`: Any? = null,
    @Json(name = "submit")
    val submit: Any? = null
)
