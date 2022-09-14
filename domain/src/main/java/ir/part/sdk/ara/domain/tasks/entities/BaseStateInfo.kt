package ir.part.sdk.ara.domain.tasks.entities

import ir.part.sdk.ara.base.util.TaskStatus

data class BaseStateInfo(
    val processType: String? = null,
    val pid: String? = null,
    val processInstanceId: String? = null,
    val relatedProcessInstanceIds: List<Any?> = listOf(),
    val status: TaskStatus = TaskStatus.NO_STATUS
)