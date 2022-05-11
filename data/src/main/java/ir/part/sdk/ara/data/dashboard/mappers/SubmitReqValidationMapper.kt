package ir.part.sdk.ara.data.dashboard.mappers

import ir.part.sdk.ara.data.dashboard.entites.SubmitReqValidationParamModel
import ir.part.sdk.ara.domain.document.entities.SubmitReqValidationParam

fun SubmitReqValidationParam.toSubmitReqValidationParamModel() = SubmitReqValidationParamModel(
    unionId = clubId
)