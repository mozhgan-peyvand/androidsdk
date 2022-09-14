package ir.part.sdk.ara.domain.tasks.entities

data class Done(
    val body: DoneInfo?,
    val keys: List<String?>?
)

data class DoneInfo(
    val date: String?,
    val id: String?,
    val type: String?,
    val processInstanceId: String?,
    val taskId: String?,
    val dashboardId: String?,
    val actorId: String?,
    val actorRole: List<String?>,
    val state: Boolean?
)