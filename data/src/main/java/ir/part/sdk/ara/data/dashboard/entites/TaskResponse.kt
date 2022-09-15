package ir.part.sdk.ara.data.dashboard.entites


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ir.part.sdk.ara.base.util.TaskStatus
import ir.part.sdk.ara.base.util.TasksName
import ir.part.sdk.ara.domain.tasks.entities.TaskInfo

@JsonClass(generateAdapter = true)
data class TaskResponse(
    @Json(name = "name")
    val name: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "dashboardIds")
    val dashboardIds: List<String>?,
    @Json(name = "attributes")
    val attributes: Attributes?,
    @Json(name = "processInstanceId")
    val processInstanceId: String?,
    @Json(name = "tags")
    val tags: List<String>?,
    @Json(name = "processId")
    val processId: String?,
    @Json(name = "org")
    val org: String?,
    @Json(name = "taskId")
    val taskId: String?,
    @Json(name = "status")
    val status: String
) {
    fun toTaskInfo() = TaskInfo(
        taskId = taskId,
        taskName = TasksName.enumValueOf(name),
        status = TaskStatus.enumValueOf(status)
    )
}

@JsonClass(generateAdapter = true)
data class Attributes(
    @Json(name = "actorUrl")
    val actorUrl: ActorUrl?,
    @Json(name = "subTasks")
    val subTasks: List<String>?,
    @Json(name = "config")
    val config: Any? = null
)

@JsonClass(generateAdapter = true)
data class ActorUrl(
    @Json(name = "get")
    val `get`: Any? = null,
    @Json(name = "submit")
    val submit: Any? = null
)
