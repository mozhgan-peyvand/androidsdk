package ir.part.sdk.ara.domain.tasks.entities

data class BaseStateInfo(
    val processType: String? = null,
    val pid: String? = null,
    val processInstanceId: String? = null,
    val relatedProcessInstanceIds: List<Any?> = listOf(),
)