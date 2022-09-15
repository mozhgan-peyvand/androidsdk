package ir.part.sdk.ara.domain.tasks.entities

import ir.part.sdk.ara.base.util.TaskStatus
import ir.part.sdk.ara.base.util.TasksName

data class TaskInfo(
    val taskId: String? = null,
    val taskName: TasksName = TasksName.NO_TASK,
    val status: TaskStatus = TaskStatus.NO_STATUS
)