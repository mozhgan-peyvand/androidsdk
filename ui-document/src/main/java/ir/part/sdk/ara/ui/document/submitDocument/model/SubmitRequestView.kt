package ir.part.sdk.ara.ui.document.submitDocument.model

import ir.part.sdk.ara.domain.document.entities.SubmitReqValidationParam

data class SubmitRequestView(
    val unionId: Int
) {
    fun toSubmitReqValidationParam() = SubmitReqValidationParam(
        clubId = unionId
    )
}
