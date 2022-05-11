package ir.part.sdk.ara.domain.tasks.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.tasks.entities.Done
import ir.part.sdk.ara.domain.tasks.entities.TaskInfo

interface TaskRepository {
    suspend fun requestGetTask(): InvokeStatus<List<TaskInfo>?>
    suspend fun requestDoingTask(processInstanceId: String, taskId: String): InvokeStatus<String?>
    suspend fun requestDoneTask(processInstanceId: String, taskId: String): InvokeStatus<Done?>


}