package ir.part.sdk.ara.domain.tasks.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.tasks.entities.Done
import ir.part.sdk.ara.domain.tasks.entities.TaskInfo

interface TaskRepository {
    suspend fun requestGetDoingTasks(): InvokeStatus<TaskInfo?>
    suspend fun requestDoingTask(): InvokeStatus<String?>
    suspend fun requestDoneTask(): InvokeStatus<Done?>
    fun getTaskInstanceId(): String
}