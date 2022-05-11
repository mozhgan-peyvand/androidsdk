package ir.part.sdk.ara.data.dashboard.entites

import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class DoingEntity(
    val actorId: String? = null,
    val dashboardId: String? = null,
    val processInstanceId: String,
    val status: String? = null,
    val taskId: String
)