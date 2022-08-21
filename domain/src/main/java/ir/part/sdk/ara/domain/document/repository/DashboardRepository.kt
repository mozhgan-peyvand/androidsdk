package ir.part.sdk.ara.domain.document.repository

import ir.part.sdk.ara.base.model.InvokeStatus
import ir.part.sdk.ara.domain.document.entities.SubmitReqValidationParam
import ir.part.sdk.ara.domain.document.entities.SubmitResponseValidation

interface DashboardRepository {
    suspend fun submitReqValidation(submitReqValidationParam: SubmitReqValidationParam): InvokeStatus<SubmitResponseValidation?>
}