package ir.part.sdk.ara.data.dashboard.entites

import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class DoingEntity(
    val taskName: String? = null,
    val processInstanceId: String,
    val taskId: String,
    val dashboardId: String? = null,
    val actorId: String? = null,
    val status: String? = null
)