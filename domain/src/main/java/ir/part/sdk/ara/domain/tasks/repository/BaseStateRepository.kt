package ir.part.sdk.ara.domain.tasks.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.tasks.entities.BaseStateInfo

interface BaseStateRepository {
    suspend fun getBaseState(): InvokeStatus<BaseStateInfo?>
    fun getProcessInstanceId(): String
}