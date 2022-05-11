package ir.part.sdk.ara.data.state.mappers

import ir.part.sdk.ara.data.state.entites.BaseStateResponse
import ir.part.sdk.ara.domain.tasks.entities.BaseStateInfo

fun BaseStateResponse.toBaseState() = BaseStateInfo(
    processType = processType,
    pid = pid,
    processInstanceId = processInstanceId,
    relatedProcessInstanceIds = relatedProcessInstanceIds
)